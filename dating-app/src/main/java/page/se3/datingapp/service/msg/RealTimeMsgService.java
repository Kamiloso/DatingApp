package page.se3.datingapp.service.msg;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.service.table.MessageTableService;

import java.util.List;

public interface RealTimeMsgService {
    void offerMessage(Message message);
    DeferredResult<@NonNull List<Message>> spawnListener();
}

@Service
@RequiredArgsConstructor
class RealTimeMsgServiceImpl implements RealTimeMsgService {

    private final MessageTableService messageTableService;

    @Override
    public void offerMessage(Message message) {
        throw new RuntimeException();
    }

    @Override
    public DeferredResult<@NonNull List<Message>> spawnListener() {
        throw new RuntimeException();
    }
}
