package org.emaginalabs.reactiveapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * User: jose
 * Date: 06/10/2018
 * Time: 18:37
 */
@Document(collection = "chats")
@Data
public class Chat {

    @Id
    private String id;

    @NotBlank
    @Size(max = 140)
    private String text;

    @NotNull
    private Date createdAt = new Date();

    public Chat() {

    }

    public Chat(String text) {
        this.id = id;
        this.text = text;
    }

}
