package rahile.abdelmounim.garage.commun.erreur;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobaleExceptionHandleur {


    @ExceptionHandler(GarageInexistantException.class)
    public ResponseEntity<ApiErrorResponse> gererGarageException(
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
    public ResponseEntity<ApiErrorResponse> gererVehiculeException(
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
    public ResponseEntity<ApiErrorResponse> gererInvalidEnumException(
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
    public ResponseEntity<ApiErrorResponse> gererVehiculeCapacitieSurpasseException(
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
    public ResponseEntity<ApiErrorResponse> gererGenericException(DataAccessException ex) {

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

        if (
                ex instanceof InvalidEnumException ||
                        ex instanceof VehiculeCapacitieSurpasseException
        ) {

            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.BAD_REQUEST;
    }


}
