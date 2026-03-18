package rahile.abdelmounim.garage.commun.erreur;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobaleExceptionGestionController {


    @ExceptionHandler(GarageInexistantException.class)
    public ResponseEntity<ApiErreurReponse> gererGarageException(
            GarageInexistantException ex) {

        ApiErreurReponse error = new ApiErreurReponse(
                ex.getCode(),
                ex.getMessage(),
                OffsetDateTime.now()
        );

        HttpStatus status = resolveStatus(ex);

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(VehiculeInexistantException.class)
    public ResponseEntity<ApiErreurReponse> gererVehiculeException(
            VehiculeInexistantException ex) {

        ApiErreurReponse error = new ApiErreurReponse(
                ex.getCode(),
                ex.getMessage(),
                OffsetDateTime.now()
        );

        HttpStatus status = resolveStatus(ex);

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(InvalidationEnumException.class)
    public ResponseEntity<ApiErreurReponse> gererInvalidEnumException(
            InvalidationEnumException ex) {

        ApiErreurReponse error = new ApiErreurReponse(
                ex.getCode(),
                ex.getMessage(),
                OffsetDateTime.now()
        );

        HttpStatus status = resolveStatus(ex);

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(VehiculeCapacitieSurpasseException.class)
    public ResponseEntity<ApiErreurReponse> gererVehiculeCapacitieSurpasseException(
            VehiculeCapacitieSurpasseException ex) {

        ApiErreurReponse error = new ApiErreurReponse(
                ex.getCode(),
                ex.getMessage(),
                OffsetDateTime.now()
        );

        HttpStatus status = resolveStatus(ex);

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiErreurReponse> gererGenericException(DataAccessException ex) {

        ApiErreurReponse error = new ApiErreurReponse(
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
                ex instanceof InvalidationEnumException ||
                        ex instanceof VehiculeCapacitieSurpasseException
        ) {

            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.BAD_REQUEST;
    }


}
