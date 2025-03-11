package com.spring.learning.social_media_blog_app.Payload;

import com.spring.learning.social_media_blog_app.DTO.PostDTO;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private List<PostDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private boolean isLastPage;


}
