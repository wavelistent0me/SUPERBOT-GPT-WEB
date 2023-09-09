
package com.cn.app.superbot.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * The type Gpt alpha model.
 */
@Data
@Accessors(chain = true)
public class GptThreeModel {


    /**
     * The Model.
     */
    private String model = "gpt-3.5-turbo";

    /**
     * The Top p.
     */
    private Double top_p = 0.9;

    /**
     * The Stream.
     */
    private boolean stream = true;

    /**
     * The Messages.
     */
    private List<Messages> messages;

    /**
     * The Max tokens.
     */
    private Integer max_tokens = 1024;


    /**
     * The type Messages.
     *
     * @author bdth
     * @email 2074055628 @qq.om
     */
    @Data
    public static class Messages {

        /**
         * The RoleCache.
         */
        private String role;

        /**
         * The Content.
         */
        private String content;
    }

}
