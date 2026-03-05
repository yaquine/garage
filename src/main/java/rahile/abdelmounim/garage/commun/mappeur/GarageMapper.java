package rahile.abdelmounim.garage.commun.mappeur;

import org.springframework.data.domain.Page;
import rahile.abdelmounim.garage.domaine.Garage;
import rahile.abdelmounim.garage.domaine.HoraireOuverture;
import rahile.abdelmounim.garage.service.dto.GarageEntreDto;
import rahile.abdelmounim.garage.service.dto.GarageSortieDto;
import rahile.abdelmounim.garage.service.dto.HoraireOuvertureDto;
import rahile.abdelmounim.garage.web.reponse.GarageReponse;
import rahile.abdelmounim.garage.web.reponse.PageResponse;
import rahile.abdelmounim.garage.web.requete.GarageRequete;
import rahile.abdelmounim.garage.web.requete.HoraireOuvertureRequete;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface GarageMapper {

    static GarageEntreDto transformerDto(GarageRequete requeteGarage) {

        Map<DayOfWeek, Set<HoraireOuvertureDto>> map = new HashMap<>();

        if (requeteGarage == null) {
            return null;
        }

        if (requeteGarage.horaireOuverturesMap() != null) {

            map =
                    requeteGarage.horaireOuverturesMap()
                            .entrySet()
                            .stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    entry -> entry.getValue()
                                            .stream()
                                            .map(GarageMapper::transformerHoraireOuverture)
                                            .collect(Collectors.toSet())
                            ));


        }

        return new GarageEntreDto(
                requeteGarage.nom(),
                requeteGarage.addresse(),
                requeteGarage.telephone(),
                requeteGarage.email(),
                map
        );
    }

    static GarageReponse transformerReponse(GarageSortieDto garageSortieDto) {
        if (garageSortieDto == null) {
            return null;
        }

        return new GarageReponse(
                garageSortieDto.id(),
                garageSortieDto.nom(),
                garageSortieDto.addresse(),
                garageSortieDto.telephone(),
                garageSortieDto.email(),
        transformerHoraireMapRequete(
                (garageSortieDto.horaireOuverturesMap())
        ));
    }

    static PageResponse<GarageReponse> transformerReponse(Page<GarageReponse> garageReponsePage) {


        return new PageResponse<>(
                garageReponsePage.getContent(),
                garageReponsePage.getNumber(),
                garageReponsePage.getSize(),
                garageReponsePage.getTotalElements(),
                garageReponsePage.getTotalPages(),
                garageReponsePage.isLast()
        );
    }

    static Garage transformerEntite(GarageEntreDto dto) {

        if (dto == null) {
            return null;
        }

        Garage garage = new Garage();

        garage.setNom(dto.nom());
        garage.setAddresse(dto.addresse());
        garage.setTelephone(dto.telephone());
        garage.setEmail(dto.email());

        Set<HoraireOuverture> horaires =
                transformerHoraireOuvertures(dto.horaireOuverturesMap());

        horaires.forEach(garage::ajouterHoraireOuverture);

        return garage;
    }

    static void transformerEntiteModifcation(Garage entity, GarageEntreDto dto) {

        if (entity == null || dto == null) {
            return;
        }

        entity.setNom(dto.nom());
        entity.setAddresse(dto.addresse());
        entity.setTelephone(dto.telephone());
        entity.setEmail(dto.email());

        Set<HoraireOuverture> horaires =
                transformerHoraireOuvertures(dto.horaireOuverturesMap());

        if (entity.getHoraireOuverturesSet() != null) {
            entity.getHoraireOuverturesSet().clear();
        }

        if (horaires != null) {
            horaires.forEach(entity::ajouterHoraireOuverture);
        }
    }


    static GarageSortieDto transformerSortieDto(Garage entity) {

        if (entity == null) {
            return null;
        }

        return new GarageSortieDto(
                entity.getId(),
                entity.getNom(),
                entity.getAddresse(),
                entity.getTelephone(),
                entity.getEmail(),
                GarageMapper.transformerHoraireOuverturesDto(entity.getHoraireOuverturesSet())
        );
    }

    private static Map<DayOfWeek, Set<HoraireOuvertureDto>> transformerHoraireOuverturesDto(
            Set<HoraireOuverture> source
    ) {

        if (source == null || source.isEmpty()) {
            return new HashMap<>();
        }

        return source.stream()
                .collect(Collectors.groupingBy(
                        HoraireOuverture::getJour,
                        Collectors.mapping(
                                GarageMapper::transformerHoraireDto,
                                Collectors.toSet()
                        )
                ));
    }


    private static HoraireOuvertureDto transformerHoraireDto(HoraireOuverture entity) {

        if (entity == null) {
            return null;
        }

        return new HoraireOuvertureDto(
                entity.getTempsDebut(),
                entity.getTempsFin()
        );
    }



    private static HoraireOuvertureDto transformerHoraireOuverture(HoraireOuvertureRequete horaireOuvertureRequete) {

        HoraireOuvertureDto embeddable = new HoraireOuvertureDto( LocalTime.parse(horaireOuvertureRequete.tempsDebut()),
                LocalTime.parse(horaireOuvertureRequete.tempsFin()));

        return embeddable;
    }


    private static Set<HoraireOuverture> transformerHoraireOuvertures(
            Map<DayOfWeek, Set<HoraireOuvertureDto>> source
    ) {

        if (source == null || source.isEmpty()) {
            return new HashSet<>();
        }

        return source.entrySet()
                .stream()
                .flatMap(entry ->
                        transformerHoraireSet(entry.getValue(), entry.getKey()).stream()
                )
                .collect(Collectors.toSet());
    }



    private static Set<HoraireOuverture> transformerHoraireSet(
            Set<HoraireOuvertureDto> source,
            DayOfWeek jour
    ) {

        if (source == null || source.isEmpty()) {
            return new HashSet<>();
        }

        return source.stream()
                .map(dto -> transformerHoraire(dto, jour))
                .collect(Collectors.toSet());
    }

    private static HoraireOuverture transformerHoraire(
            HoraireOuvertureDto dto,
            DayOfWeek jour
    ) {

        if (dto == null) {
            return null;
        }

        HoraireOuverture entity = new HoraireOuverture();

        entity.setJour(jour);

        entity.setTempsDebut(dto.tempsDebut());

        entity.setTempsFin(dto.tempsFin());

        return entity;
    }



    private static HoraireOuverture transformerHoraireOuverture(HoraireOuvertureDto dto) {

        if (dto == null) {
            return null;
        }

        HoraireOuverture entity = new HoraireOuverture();

        entity.setTempsDebut(dto.tempsDebut());
        entity.setTempsFin(dto.tempsFin());

        return entity;
    }


    private static Map<DayOfWeek, Set<HoraireOuvertureRequete>> transformerHoraireMapRequete(
            Map<DayOfWeek, Set<HoraireOuvertureDto>> source
    ) {

        if (source == null || source.isEmpty()) {
            return Map.of();
        }

        return source.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> transformerHoraireSetRequete(entry.getValue())
                ));
    }


    private static Set<HoraireOuvertureRequete> transformerHoraireSetRequete(
            Set<HoraireOuvertureDto> source
    ) {

        if (source == null || source.isEmpty()) {
            return Set.of();
        }

        return source.stream()
                .map(GarageMapper::transformerHoraireRequete)
                .collect(Collectors.toSet());
    }


    private static HoraireOuvertureRequete transformerHoraireRequete(HoraireOuvertureDto dto) {

        if (dto == null) {
            return null;
        }

        return new HoraireOuvertureRequete(
                formatTime(dto.tempsDebut()),
                formatTime(dto.tempsFin())
        );
    }

    private static String formatTime(LocalTime time) {
        return time != null ? time.toString() : null;
    }
}
