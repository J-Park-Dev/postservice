package hello.postservice.web;

import hello.postservice.config.auth.LoginUser;
import hello.postservice.config.auth.dto.SessionUser;
import hello.postservice.service.posts.PostsService;
import hello.postservice.web.dto.PostsResponseDto;
import hello.postservice.web.dto.PostsSaveRequestDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSaveGet(Model model) {
        model.addAttribute("posts", new PostsSaveRequestDto());
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdateGet(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts", dto);
        return "posts-update";
    }

}
