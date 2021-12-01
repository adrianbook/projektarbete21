public class ToiletTooCloseException extends Exception {
    private String message;
    public ToiletTooCloseException(){
    this.message = "toilet is too close";
    }
    public String getMessage(){
    return message;
    }
}