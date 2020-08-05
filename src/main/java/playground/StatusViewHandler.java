package playground;

import playground.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusViewHandler {


    @Autowired
    private StatusRepository statusRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenScheduleCreated_then_CREATE_1 (@Payload ScheduleCreated scheduleCreated) {
        try {
            if (scheduleCreated.isMe()) {
                // view 객체 생성
                Status status = new Status();
                // view 객체에 이벤트의 Value 를 set 함
                status.setId(scheduleCreated.getId());
                status.setGroundId(scheduleCreated.getGroundId());
                status.setDate(scheduleCreated.getDate());
                status.setGroundType(scheduleCreated.getGroundType());
                status.setMaxPlayer(scheduleCreated.getMaxPlayer());
                status.setStatus(scheduleCreated.getStatus());
                // view 레파지 토리에 save
                statusRepository.save(status);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenScheduleUpdated_then_UPDATE_1(@Payload ScheduleUpdated scheduleUpdated) {
        try {
            if (scheduleUpdated.isMe()) {
                // view 객체 조회
                List<Status> statusList = statusRepository.findByGroundId(scheduleUpdated.getGroundId());
                for(Status status : statusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    status.setStatus(scheduleUpdated.getStatus());
                    status.setUserName(scheduleUpdated.getUserName());
                    // view 레파지 토리에 save
                    statusRepository.save(status);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*@StreamListener(KafkaProcessor.INPUT)
    public void whenBookCanceled_then_UPDATE_2(@Payload BookCanceled bookCanceled) {
        try {
            if (bookCanceled.isMe()) {
                // view 객체 조회
                List<Status> statusList = statusRepository.findByGroundId(bookCanceled.getGroundId());
                for(Status status : statusList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    status.setStatus("Available");
                    status.setUserName(bookCanceled.getUserName());
                    // view 레파지 토리에 save
                    statusRepository.save(status);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    @StreamListener(KafkaProcessor.INPUT)
    public void whenGroundDeleted_then_DELETE_1(@Payload GroundDeleted groundDeleted) {
        try {
            if (groundDeleted.isMe()) {
                // view 레파지 토리에 삭제 쿼리
                statusRepository.deleteByGroundId(groundDeleted.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
/*
    @StreamListener(KafkaProcessor.INPUT)
    public void whenScheduleDeleted_then_DELETE_1(@Payload ScheduleDeleted scheduleDeleted) {
        try {
            if (scheduleDeleted.isMe()) {
                // view 레파지 토리에 삭제 쿼리
                statusRepository.deleteByGroundId(scheduleDeleted.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}