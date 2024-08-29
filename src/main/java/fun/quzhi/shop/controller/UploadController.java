package fun.quzhi.shop.controller;

import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Tag(name = "文件上传")
@Controller
public class UploadController {

    @Value("${app.file-upload-uri}")
    String fileUploadURI;

    @PostMapping("admin/upload/file")
    @ResponseBody
    public CommonResponse uploadFile(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String  uuidFileName =  UUID.randomUUID().toString() + ext;
        File fileDir = new File(Constant.FILE_UPLOAD_PATH);
        File destFile = new File(Constant.FILE_UPLOAD_PATH + uuidFileName);
        createFile(file, fileDir, destFile);
        String fileUrl = fileUploadURI + "/files/" + uuidFileName;
        // TODO, 入库
        return CommonResponse.success(fileUrl);
    }

    @PostMapping("admin/upload/image")
    @ResponseBody
    public CommonResponse uploadImage(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String  uuidFileName =  UUID.randomUUID().toString() + ext;
        File fileDir = new File(Constant.FILE_UPLOAD_PATH);
        File destFile = new File(Constant.FILE_UPLOAD_PATH + uuidFileName);
        createFile(file, fileDir, destFile);
        Thumbnails.of(destFile)
                .size(Constant.IMAGE_SIZE, Constant.IMAGE_SIZE)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(Constant.FILE_UPLOAD_PATH + Constant.WATER_MARK_JPG)), Constant.IMAGE_OPACITY)
                .toFile(new File(Constant.FILE_UPLOAD_PATH + uuidFileName));
        String fileUrl = fileUploadURI + "/files/" + uuidFileName;
        // TODO, 入库
        return CommonResponse.success(fileUrl);
    }

    private static void createFile(MultipartFile file, File fileDir, File destFile) {
        if (!fileDir.exists() && !fileDir.mkdir()) {
            throw new ShopException(ShopExceptionEnum.UPLOAD_DIR_ERROR);
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private URI getHost(URI uri) {
        URI host;
        try {
            host = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (URISyntaxException e) {
            host = null;
        }
        return host;
    }
}
