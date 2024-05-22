package fun.quzhi.shop.controller;

import fun.quzhi.shop.common.CommonResponse;
import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Tag(name = "文件上传")
@Controller
public class UploadController {
    @PostMapping("admin/upload/file")
    @ResponseBody
    public CommonResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String  uuidFileName =  UUID.randomUUID().toString() + ext;
        File fileDir = new File(Constant.FILE_UPLOAD_PATH);
        File destFile = new File(Constant.FILE_UPLOAD_PATH + uuidFileName);
        if (!fileDir.exists() && !fileDir.mkdir()) {
            throw new ShopException(ShopExceptionEnum.UPLOAD_DIR_ERROR);
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileUrl;
        try {
            fileUrl = getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/files/" + uuidFileName;
            // TODO, 入库
        } catch(URISyntaxException e) {
            e.printStackTrace();
            return CommonResponse.error(ShopExceptionEnum.UPLOAD_FILE_FAILED);
        }

        return CommonResponse.success(fileUrl);
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
