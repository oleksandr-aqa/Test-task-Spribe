package dto.reponse;

public class BaseResponse {
    protected String message;
    protected String contentLength;

    public String getMessage() {
        return message;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }
}
