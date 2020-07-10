package mk.ukim.finki.emt.lab.infra.eventlog;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProcessedRemoteEventRepository extends JpaRepository<ProcessedRemoteEvent, String> {
}
