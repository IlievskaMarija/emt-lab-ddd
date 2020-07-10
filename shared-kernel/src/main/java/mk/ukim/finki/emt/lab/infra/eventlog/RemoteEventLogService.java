package mk.ukim.finki.emt.lab.infra.eventlog;

import org.springframework.lang.NonNull;


public interface RemoteEventLogService {

    @NonNull
    String source();

    @NonNull
    RemoteEventLog currentLog();
}
