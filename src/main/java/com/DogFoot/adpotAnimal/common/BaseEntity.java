package main.java.com.DogFoot.adpotAnimal.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity {
    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    // TODO: updateUpdate_at...? <- 업데이트라는 말을 두번..
    public void updateUpdated_at(BaseEntity baseEntity){
        this.updated_at = baseEntity.created_at;
    }
}
