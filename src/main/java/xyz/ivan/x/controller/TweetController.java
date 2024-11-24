package xyz.ivan.x.controller;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.ivan.x.entity.Tweet;
import xyz.ivan.x.service.ITweetService;
import xyz.ivan.x.util.Result;
import xyz.ivan.x.util.SystemConstants;
import xyz.ivan.x.util.UserHolder;

import java.io.*;
import java.nio.file.Paths;

@RestController
@Slf4j
@RequestMapping("v1/")
public class TweetController {
    @Autowired
    private ITweetService tweetService;

    @GetMapping("tweet/{id}")
    public Result getTweetById(@PathVariable("id") Long id){
        Long userId = UserHolder.getUser().getId();
        Tweet tweet = tweetService.getById(id);
        if (tweet == null) {
            log.error("用户：{}，未查询到该id的tweet：{}",userId,id);
            return Result.fail(404, "未查询到该id的tweet");
        }
        log.info("用户：{}，查询该id的tweet：{}",userId,id);
        return Result.ok(tweet);
    }

    @PostMapping("upload")
    public Result uploadImage(@RequestParam("file") MultipartFile file){
        Long userId = UserHolder.getUser().getId();
        if (file == null) {
            log.error("用户：{}上传图片失败！", userId);
            return Result.fail(401,"上传图片失败！");
        }
        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("image/")){
            log.error("用户：{}上传的文件类型错误，应该是图片类型，而不是{}类型",userId,contentType);
            return Result.fail(401,"上传文件类型错误！请上传图片类型！");
        }

        File savePath = new File(SystemConstants.TWEETS_IMAGES_DIR);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }

        String extension = FileNameUtil.extName(file.getOriginalFilename());

        String fileName = Paths.get(SystemConstants.TWEETS_IMAGES_DIR, RandomUtil.randomString(10) + "." + extension).toString();

        try ( FileOutputStream fileOutputStream = new FileOutputStream(fileName) ){
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            log.error("用户：{}上传文件失败！",userId);
            throw new RuntimeException(e);
        }
        log.info("用户：{}上传图片：{}成功！",userId,fileName);
        return Result.ok(fileName);
    }

    @PostMapping("/tweet/save")
    public Result save(@RequestBody Tweet tweet){
        return tweetService.saveTweet(tweet);
    }

    @DeleteMapping("/tweet/remove/{id}")
    public Result removeById(@PathVariable Long id){
        return tweetService.removeTweet(id);
    }

    @PutMapping("/tweet/update")
    public Result updateById(@RequestBody Tweet tweet){
        return tweetService.updateTweet(tweet);
    }

    @GetMapping("/tweet/list")
    public Result tweetList(){
        return tweetService.tweetList();
    }

    @GetMapping("/tweet/listByUser")
    public Result listByUser(){
        return tweetService.listByUser();
    }
}
