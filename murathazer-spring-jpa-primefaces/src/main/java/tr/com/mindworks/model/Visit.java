package tr.com.mindworks.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import tr.com.mindworks.annotations.SpringSessionScoped;

/**
 * @author mhazer on 26/03/2017.
 */
@Component
@SpringSessionScoped
public class Visit implements Serializable
{
    @Setter @Getter
    private TUser user;
}
