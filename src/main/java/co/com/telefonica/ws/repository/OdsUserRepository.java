package co.com.telefonica.ws.repository;

import co.com.telefonica.ws.domain.OdsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface OdsUserRepository extends JpaRepository<OdsUser, String> {

    @Query(value = "SELECT * FROM DWHODS.DETALLE_DOCUMENTOS_FS_BASE WHERE LOAD_DATE = :loadDate ORDER BY ID_TYPE, ID_NUMBER", nativeQuery = true)
    Page<OdsUser> findByLoadDate(Date loadDate, Pageable pageable);

}
