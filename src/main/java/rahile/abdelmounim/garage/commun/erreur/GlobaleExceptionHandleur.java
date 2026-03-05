package rahile.abdelmounim.garage.commun.erreur;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobaleExceptionHandleur {


    @ExceptionHandler(GarageInexistantException.class)
    public ResponseEntity<ApiErrorResponse> handleGarageException(
            GarageInexistantException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                OffsetDateTime.now()
        );

        HttpStatus status = resolveStatus(ex);

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(VehiculeInexistantException.class)
    public ResponseEntity<ApiErrorResponse> handleVehiculeException(
            VehiculeInexistantException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                OffsetDateTime.now()
        );

        HttpStatus status = resolveStatus(ex);

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(InvalidEnumException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidEnumException(
            InvalidEnumException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                OffsetDateTime.now()
        );

        HttpStatus status = resolveStatus(ex);

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(VehiculeCapacitieSurpasseException.class)
    public ResponseEntity<ApiErrorResponse> handleVehiculeCapacitieSurpasseException(
            VehiculeCapacitieSurpasseException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                OffsetDateTime.now()
        );

        HttpStatus status = resolveStatus(ex);

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(DataAccessException ex) {

        ApiErrorResponse error = new ApiErrorResponse(
                "INTERNAL_ERROR",
                "An unexpected error occurred: " + ex.getMessage(),
                OffsetDateTime.now()
        );

        HttpStatus status = resolveStatus(ex);

        return new ResponseEntity<>(error, status);
    }


    private HttpStatus resolveStatus(RuntimeException ex) {

        if (ex instanceof GarageInexistantException) {
            return HttpStatus.NOT_FOUND;
        }


        if (ex instanceof BusinessException ||
                ex instanceof InvalidEnumException ||
                ex instanceof VehiculeCapacitieSurpasseException
        ) {


            return HttpStatus.BAD_REQUEST;
        }


        return HttpStatus.BAD_REQUEST;
    }


}
