/*
*  Class contains main fields about Json error
* <p>
*  This class can be just making new errors
* */
class JsonError {

    private int errorCode;
    private String errorMessage;
    private String errorPlace;
    private String resource;
    private int request_id;

    /*
    * Constructor is fill all fields of error
    * */
    JsonError(int errorCode, String errorMessage, String errorPlace, String resource, int request_id) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorPlace = errorPlace;
        this.resource = resource;
        this.request_id = request_id;
    }
}
