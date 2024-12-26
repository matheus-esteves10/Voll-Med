package med.voll.api.repository;

import med.voll.api.model.medico.Especialidade;
import med.voll.api.model.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            SELECT m from Medico m 
            where m.ativo = true and
            m.especialidade = :especialidade and
            m.id not in (
                SELECT c.medico.id from Consulta c 
                where c.data = :data 
            )
            order by rand()
            limit 1
            """)
    Medico escolherMedicoAleatorio(Especialidade especialidade, LocalDateTime data);

    @Query("select m.ativo from Medico m where m.id = :idMedico")
    Boolean findAtivoById(Long idMedico);
}
