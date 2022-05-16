package uy.maly.wishlist.log;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author JMaly
 * @project wishlist
 */

/**
 * Doogies very cool HTTP request logging
 * <p>
 * There is also {@link org.springframework.web.filter.CommonsRequestLoggingFilter}  but it cannot log request method
 * And it cannot easily be extended.
 * <p>
 * https://mdeinum.wordpress.com/2015/07/01/spring-framework-hidden-gems/
 * http://stackoverflow.com/questions/8933054/how-to-read-and-copy-the-http-servlet-response-output-stream-content-for-logging
 */

@Component
public class CustomFormatLogger extends OncePerRequestFilter {

    private boolean includeResponsePayload = true;
    private int maxPayloadLength = 1000;

    private String getContentAsString(byte[] buf, int maxLength, String charsetName) {
        if (buf == null || buf.length == 0) return "";
        int length = Math.min(buf.length, this.maxPayloadLength);
        try {
            return new String(buf, 0, length, charsetName);
        } catch (UnsupportedEncodingException ex) {
            return "Unsupported Encoding";
        }
    }

    /**
     * Log each request and respponse with full Request URI, content payload and duration of the request in ms.
     *
     * @param request     the request
     * @param response    the response
     * @param filterChain chain of filters
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        StringBuffer sb = new StringBuffer();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,sss");
        String time = sdf.format(date);

        sb.append(" | tsf_time=\"" + time + "\"");
        sb.append(" requestid=\"" + startTime % 10000 + "\"");
        sb.append(" application=\"wishlist\"");
        sb.append(" httpUri=\"" + request.getRequestURL() + "\"");
        String queryString = (request.getQueryString() != null) ? request.getQueryString() : "";
        sb.append(" queryString=\"" + queryString + "\"");
        sb.append(" method=\"" + request.getMethod() + "\"");

        // ========= Log request and response payload ("body") ========
        // We CANNOT simply read the request payload here, because then the InputStream would be consumed and cannot be read again by the actual processing/server.
        //    String reqBody = DoogiesUtil._stream2String(request.getInputStream());   // THIS WOULD NOT WORK!
        // So we need to apply some stronger magic here :-)
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);     // ======== This performs the actual request!
        double duration = (System.currentTimeMillis() - startTime) / 1000.0;

        if (!(request.getRequestURL().toString().contains("signup") || request.getRequestURL().toString().contains("signin"))) {
            String principalName = (request.getUserPrincipal() != null) ? request.getUserPrincipal().getName() : "";
            sb.append(" principalName=\"" + principalName + "\"");
            // I can only log the request's body AFTER the request has been made and ContentCachingRequestWrapper did its work.
            String requestBody = this.getContentAsString(wrappedRequest.getContentAsByteArray(), this.maxPayloadLength, request.getCharacterEncoding());
            if (requestBody.length() > 0) {
                //this.logger.info("   Request body:\n" +requestBody);
                sb.append(" request=\"" + URLEncoder.encode(requestBody, "UTF-8") + "\"");
            }
            if (includeResponsePayload) {
                byte[] buf = wrappedResponse.getContentAsByteArray();
                sb.append(" response=\"" + URLEncoder.encode(getContentAsString(buf, this.maxPayloadLength, response.getCharacterEncoding()), "UTF-8") + "\"");
            }
        }
        wrappedResponse.copyBodyToResponse();  // IMPORTANT: copy content of response back into original response
        sb.append(" n_responseCode=" + response.getStatus());
        sb.append(" n_roundTripSeconds=" + duration);
        this.logger.info(sb);
    }
}
