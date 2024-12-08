package com.moloom.img.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.tika.metadata.Metadata;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author: moloom
 * @date: 2024-11-09 19:24
 * @description: 摄像头信息
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
public class MetadataEntity {
    private Long metadataId; // 主键
    private String description;    //图片描述
    private String make;        //摄像头品牌
    private String model;       //摄像头型号
    private String software;    //固件 Firmware 版本或者编辑软件
    private String documentarySoftware;     //文档生成程序
    private String artist;      //艺人
    private String title;       //标题
    private String keywords;    //关键词,有多个
    private String copyright;       //版权
    private String orientation;     //旋转角度
    private Double xResolution;     //水平分辨率
    private Double yResolution;     //垂直分辨率
    private String resolutionUnit;  //分辨率单位
    private String yCbCrPositioning;        //色相位置
    private String componentsConfiguration;     //颜色配置
    private String compressedBitsPerPixel;      //平均压缩率
    private String colorSpace;      //颜色空间
    private String compression;     //压缩方式

    private String exifVersion;     //EXIF版本
    private String shutterSpeed;    //快门速度
    private Integer isoSpeedRatings;    //ISO曝光度
    private String exposureTime;    //曝光时间，快门速度
    private String exposureMode;     //曝光模式
    private String exposureBiasValue;       //曝光偏差
    private Float fNumber;     //光圈系数
    private String maxApertureValue;        //最大光圈
    private String meteringMode;        //测光模式
    private String lightSource;     //光源，白平衡设置
    private Float Brightness;       //亮度
    private String whiteBalance;    //白平衡
    private String focalLength;     //镜头物理焦距
    private String focalLength35;   //35mm 等效焦距
    private String flash;       //闪光灯描述
    private Boolean flashValue;      //是否开启闪光灯
    private String flashPixVersion;     //FlashPix版本
    private String sceneCaptureType;    //拍摄类型
    private String LensMake;        //镜头生产厂商
    private String LensModel;       //镜头型号
    private String lensSpecification;   //镜头焦距信息
    private Timestamp dateTimeOriginal;     //拍摄时间
    private Integer subsecTimeOriginal;     //拍摄时间 微秒数
    private Timestamp dateTimeDigitized;    //数字化时间
    private Integer subsecTimeDigitized;    //数字化时间 微秒数

    private Long createdBy;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    //不入数据库
    private Integer width;     //图像宽度
    private Integer length;    //图像高度

    /**
     * @param metadata 提取的图像信息对象
     * @return
     * @author moloom
     * @date 2024-11-17 23:04:00
     * @description 从 Metadata 对象提取信息
     */
    public MetadataEntity fromMetadata(Metadata metadata) {
        if (metadata == null)
            return null;

        this.setDescription(metadata.get("Exif IFD0:Image Description"));
        this.setMake(metadata.get("tiff:Make"));
        this.setModel(metadata.get("Exif IFD0:Model"));
        if (metadata.get("Version") != null)
            this.setSoftware(metadata.get("Version"));
        else if (metadata.get("Firmware Version") != null)
            this.setSoftware(metadata.get("Firmware Version"));
        if (metadata.get("tiff:Software") != null)
            this.setDocumentarySoftware(metadata.get("tiff:Software"));
        else if (metadata.get("Exif IFD0:Software") != null)
            this.setDocumentarySoftware(metadata.get("Exif IFD0:Software"));
        else if (metadata.get("xmpMM:History:SoftwareAgent") != null)
            this.setDocumentarySoftware(metadata.get("xmpMM:History:SoftwareAgent"));
        this.setArtist(metadata.get("Exif IFD0:Artist"));
        this.setTitle(metadata.get("dc:title"));
        this.setKeywords(metadata.get("Keywords"));
        this.setCopyright(metadata.get("Exif IFD0:Copyright"));
        this.setOrientation(metadata.get("tiff:Orientation"));
        if (metadata.get("tiff:XResolution") != null)
            this.setXResolution(Double.valueOf(metadata.get("tiff:XResolution")));
        if (metadata.get("tiff:YResolution") != null)
            this.setYResolution(Double.valueOf(metadata.get("tiff:YResolution")));
        this.setResolutionUnit(metadata.get("tiff:ResolutionUnit"));
        this.setYCbCrPositioning(metadata.get("Exif IFD0:YCbCr Positioning"));
        this.setComponentsConfiguration(metadata.get("Exif SubIFD:Components Configuration"));
        this.setCompressedBitsPerPixel(metadata.get("Exif SubIFD:Compressed Bits Per Pixel"));
        this.setColorSpace(metadata.get("Exif SubIFD:Color Space"));
        this.setCompression(metadata.get("Compression Type"));

        this.setExifVersion(metadata.get("Exif SubIFD:Exif Version"));
        this.setShutterSpeed(metadata.get("Exif SubIFD:Shutter Speed Value"));
        if (metadata.get("exif:IsoSpeedRatings") != null)
            this.setIsoSpeedRatings(Integer.valueOf(metadata.get("exif:IsoSpeedRatings")));
        this.setExposureTime(metadata.get("Exif SubIFD:Exposure Time"));
        this.setExposureMode(metadata.get("Exif SubIFD:Exposure Mode"));
        this.setExposureBiasValue(metadata.get("Exif SubIFD:Exposure Bias Value"));
        if (metadata.get("exif:FNumber") != null)
            this.setFNumber(Float.valueOf(metadata.get("exif:FNumber")));
        this.setMaxApertureValue(metadata.get("Exif SubIFD:Aperture Value"));
        this.setMeteringMode(metadata.get("Exif SubIFD:Metering Mode"));
//        this.setLightSource(metadata.get("Light Source"));
        this.setWhiteBalance(metadata.get("Exif SubIFD:White Balance Mode"));
        if (metadata.get("Exif SubIFD:Brightness Value") != null)
            this.setBrightness(Float.valueOf(metadata.get("Exif SubIFD:Brightness Value")));
        this.setFocalLength(metadata.get("Exif SubIFD:Focal Length"));
        this.setFocalLength35(metadata.get("Exif SubIFD:Focal Length 35"));
        this.setFlash(metadata.get("Exif SubIFD:Flash"));
        if (metadata.get("exif:Flash") != null)
            this.setFlashValue(Boolean.valueOf(metadata.get("exif:Flash")));
        this.setFlashPixVersion(metadata.get("Exif SubIFD:FlashPix Version"));
        this.setSceneCaptureType(metadata.get("Exif SubIFD:Scene Capture Type"));
        this.setLensMake(metadata.get("Exif SubIFD:Lens Make"));
        this.setLensModel(metadata.get("Exif SubIFD:Lens Model"));
        this.setLensSpecification(metadata.get("Exif SubIFD:Lens Specification"));
        this.setDateTimeOriginal(convertStringToTimestamp(metadata.get("Exif SubIFD:Date/Time Original")));
        if (metadata.get("Exif SubIFD:Sub-Sec Time Original") != null)
            this.setSubsecTimeOriginal(Integer.valueOf(metadata.get("Exif SubIFD:Sub-Sec Time Original")));
        if (metadata.get("Exif SubIFD:Date/Time Digitized") != null)
            this.setDateTimeDigitized(convertStringToTimestamp(metadata.get("Exif SubIFD:Date/Time Digitized")));
        else if (metadata.get("Exif IFD0:Date/Time") != null)
            this.setDateTimeDigitized(convertStringToTimestamp(metadata.get("Exif IFD0:Date/Time")));
        if (metadata.get("Exif SubIFD:Sub-Sec Time Digitized") != null)
            this.setSubsecTimeDigitized(Integer.valueOf(metadata.get("Exif SubIFD:Sub-Sec Time Digitized")));

        if (metadata.get("tiff:ImageWidth") != null)
            this.setWidth(Integer.valueOf(metadata.get("tiff:ImageWidth")));
        if (metadata.get("tiff:ImageLength") != null)
            this.setLength(Integer.valueOf(metadata.get("tiff:ImageLength")));
        return this;
    }

    /**
     * @param str     to be parsed
     * @param pattern format pattern of str
     * @return Timestamp
     * @author moloom
     * @date 2024-11-18 21:07:13
     * @description Convert string to Timestamp by custom pattern
     */
    @Nullable
    public static Timestamp convertStringToTimestamp(String str, String pattern) {
        if (str == null || str.isBlank() || pattern == null || pattern.isBlank()) return null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date parsedDate = dateFormat.parse(str);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException("parsing String to TimeStamp error");
        }
    }

    // 如果字符串已经是 "yyyy:MM:dd HH:mm:ss" 格式

    /**
     * @param str to be parsed
     * @return Timestamp
     * @author moloom
     * @date 2024-11-18 21:13:22
     * @description Convert string to Timestamp with automation parsing
     */
    @Nullable
    public static Timestamp convertStringToTimestamp(String str) {
        if (str == null)
            return null;
        // 正则表达式匹配两种格式
        Pattern iso8601Pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]\\d{2}:\\d{2}");
        Pattern defaltPattern = Pattern.compile("\\d{4}:\\d{2}:\\d{2} \\d{2}:\\d{2}:\\d{2}");
        if (iso8601Pattern.matcher(str).matches())
            try {
                return Timestamp.from(OffsetDateTime.parse(str).toInstant());
            } catch (Exception e) {
                throw new RuntimeException("parsing String to TimeStamp error");
            }
        else if (defaltPattern.matcher(str).matches())
            return convertStringToTimestamp(str, "yyyy:MM:dd HH:mm:ss");
        return null;
    }
}
