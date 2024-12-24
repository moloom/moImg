package com.moloom.img.api.service.impl;

import com.moloom.img.api.service.MinioService;
import com.moloom.img.api.to.Buckets;
import com.moloom.img.api.vo.DownloadVO;
import com.moloom.img.api.vo.UploadVo;
import io.minio.*;
import io.minio.messages.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: moloom
 * @date: 2024-10-28 19:40
 * @description: operations of minio
 */
@Service("minioService")
@Slf4j
public class MinioServiceImpl implements MinioService {

    @Resource
    private MinioClient minioClient;

    @Override
    public boolean bucketExists(String bucketName) {
        try {
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build());
            if (!bucketExists)
                log.error("checkBucketExist()::minIO bucket {} is not exist", bucketName);
            return bucketExists;
        } catch (Exception e) {
            log.error("checkBucketExist()::check minIO bucket error.please check minio status is running.");
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteBucketEncryption(String bucketName) {
        try {
            minioClient.deleteBucketEncryption(
                    DeleteBucketEncryptionArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("deleteBucketEncryption()::delete bucket encryption error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBucketLifecycle(String bucketName) {
        try {
            minioClient.deleteBucketLifecycle(
                    DeleteBucketLifecycleArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("deleteBucketLifecycle()::delete bucket lifecycle error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBucketTags(String bucketName) {
        try {
            minioClient.deleteBucketTags(DeleteBucketTagsArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            log.error("deleteBucketTags()::delete bucket tags error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBucketPolicy(String bucketName) {
        try {
            minioClient.deleteBucketPolicy(DeleteBucketPolicyArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            log.error("deleteBucketPolicy()::delete bucket policy error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBucketReplication(String bucketName) {
        try {
            minioClient.deleteBucketReplication(DeleteBucketReplicationArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            log.error("deleteBucketReplication()::delete bucket replication error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBucketNotification(String bucketName) {
        try {
            minioClient.deleteBucketNotification(
                    DeleteBucketNotificationArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("deleteBucketNotification()::delete bucket notification configuration error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteObjectLockConfiguration(String bucketName) {
        try {
            minioClient.deleteObjectLockConfiguration(
                    DeleteObjectLockConfigurationArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("deleteObjectLockConfiguration()::delete object lock configuration error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public SseConfiguration getBucketEncryption(String bucketName) {
        try {
            return minioClient.getBucketEncryption(
                    GetBucketEncryptionArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("getBucketEncryption()::get bucket encryption error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public LifecycleConfiguration getBucketLifecycle(String bucketName) {
        try {
            return minioClient.getBucketLifecycle(
                    GetBucketLifecycleArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("getBucketLifecycle()::get bucket lifecycle error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public NotificationConfiguration getBucketNotification(String bucketName) {
        try {
            return minioClient.getBucketNotification(
                    GetBucketNotificationArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("getBucketNotification()::get bucket notification configuration error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBucketPolicy(String bucketName) {
        try {
            return minioClient.getBucketPolicy(
                    GetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("getBucketPolicy()::get bucket policy error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReplicationConfiguration getBucketReplication(String bucketName) {
        try {
            return minioClient.getBucketReplication(
                    GetBucketReplicationArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("getBucketReplication()::get bucket replication error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tags getBucketTags(String bucketName) {
        try {
            return minioClient.getBucketTags(
                    GetBucketTagsArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("getBucketTags()::get bucket tags error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public VersioningConfiguration getBucketVersioning(String bucketName) {
        try {
            return minioClient.getBucketVersioning(
                    GetBucketVersioningArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("getBucketVersioning()::get bucket versioning error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLockConfiguration getObjectLockConfiguration(String bucketName) {
        try {
            return minioClient.getObjectLockConfiguration(
                    GetObjectLockConfigurationArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("getObjectLockConfiguration()::get object lock configuration error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Bucket> listBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            log.error("listBuckets()::list buckets error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Bucket> listBuckets(Map<String, String> extraHeaders, Map<String, String> extraQueryParams) {
        try {
            return minioClient.listBuckets(
                    ListBucketsArgs.builder()
                            .extraHeaders(extraHeaders)
                            .extraQueryParams(extraQueryParams)
                            .build());
        } catch (Exception e) {
            log.error("listBuckets()::list buckets error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public CloseableIterator<Result<NotificationRecords>> listenBucketNotification(@NotNull String bucketName, @Nullable String prefix, @Nullable String suffix, String[] events) {
        try {
            return minioClient.listenBucketNotification(
                    ListenBucketNotificationArgs.builder()
                            .bucket(bucketName)
                            .prefix(prefix)
                            .suffix(suffix)
                            .events(events)
                            .build());
        } catch (Exception e) {
            log.error("listenBucketNotification()::listen bucket notification error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Result<Item>> listObjects(ListObjectsArgs args) {
        return minioClient.listObjects(args);
    }

    @Override
    public void makeBucket(@NotNull String bucketName, @Nullable String region, @Nullable Boolean objectLock) {
        try {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .region(region)
                            .objectLock(objectLock)
                            .build());
        } catch (Exception e) {
            log.error("makeBucket()::make bucket error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(
                    RemoveBucketArgs.builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            log.error("removeBucket()::remove bucket error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketEncryption(SetBucketEncryptionArgs args) {
        try {
            minioClient.setBucketEncryption(args);
        } catch (Exception e) {
            log.error("setBucketEncryption()::set bucket encryption error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketLifecycle(SetBucketLifecycleArgs args) {
        try {
            minioClient.setBucketLifecycle(args);
        } catch (Exception e) {
            log.error("setBucketLifecycle()::set bucket lifecycle error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketNotification(SetBucketNotificationArgs args) {
        try {
            minioClient.setBucketNotification(args);
        } catch (Exception e) {
            log.error("setBucketNotification()::set bucket notification error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketPolicy(SetBucketPolicyArgs args) {
        try {
            minioClient.setBucketPolicy(args);
        } catch (Exception e) {
            log.error("setBucketPolicy()::set bucket policy error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketReplication(SetBucketReplicationArgs args) {
        try {
            minioClient.setBucketReplication(args);
        } catch (Exception e) {
            log.error("setBucketReplication()::set bucket replication error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketTags(Buckets args) {
        try {
            minioClient.setBucketTags(SetBucketTagsArgs.builder()
                    .bucket(args.getBucketName())
                    .tags(args.getBucketTags())
                    .build());
        } catch (Exception e) {
            log.error("setBucketTags()::set bucket tags error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketVersioning(SetBucketVersioningArgs args) {
        try {
            minioClient.setBucketVersioning(args);
        } catch (Exception e) {
            log.error("setBucketVersioning()::set bucket versioning error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setObjectLockConfiguration(SetObjectLockConfigurationArgs args) {
        try {
            minioClient.setObjectLockConfiguration(args);
        } catch (Exception e) {
            log.error("setObjectLockConfiguration()::set object lock configuration error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectWriteResponse composeObject(ComposeObjectArgs args) {
        try {
            return minioClient.composeObject(args);
        } catch (Exception e) {
            log.error("composeObject()::compose object error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectWriteResponse copyObject(CopyObjectArgs args) {
        try {
            return minioClient.copyObject(args);
        } catch (Exception e) {
            log.error("copyObject()::copy object error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteObjectTags(String bucketName, String objectName) {
        try {
            minioClient.deleteObjectTags(DeleteObjectTagsArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            log.error("deleteObjectTags()::delete object tags error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disableObjectLegalHold(String bucketName, String objectName) {
        try {
            minioClient.disableObjectLegalHold(DisableObjectLegalHoldArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            log.error("disableObjectLegalHold()::disable object legal hold error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enableObjectLegalHold(String bucketName, String objectName) {
        try {
            minioClient.enableObjectLegalHold(EnableObjectLegalHoldArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            log.error("enableObjectLegalHold()::enable object legal hold error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getObject(DownloadVO vo) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(vo.getBucketName())
                            .object(vo.getStoragePath())
                            .build());
        } catch (Exception e) {
            log.error("getObject()::get object from minIO error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void downloadObject(@NotNull String bucketName, @NotNull String objectName, @NotNull String destinationPath, ServerSideEncryptionCustomerKey ssec) {
        try {
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .ssec(ssec)
                            .filename(destinationPath)
                            .build());
        } catch (Exception e) {
            log.error("downloadObject()::download object from minIO error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Retention getObjectRetention(@NotNull String bucketName, @NotNull String objectName, @NotNull String versionId) {
        try {
            return minioClient.getObjectRetention(GetObjectRetentionArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .versionId(versionId)
                    .build());
        } catch (Exception e) {
            log.error("getObjectRetention()::get object retention error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tags getObjectTags(String bucketName, String objectName) {
        try {
            return minioClient.getObjectTags(GetObjectTagsArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            log.error("getObjectTags()::get object tags error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPresignedObjectUrl(GetPresignedObjectUrlArgs args) {
        try {
            return minioClient.getPresignedObjectUrl(args);
        } catch (Exception e) {
            log.error("getPresignedObjectUrl()::get presigned object url error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isObjectLegalHoldEnabled(@NotNull String bucketName, @NotNull String objectName, @NotNull String versionId) {
        try {
            return minioClient.isObjectLegalHoldEnabled(IsObjectLegalHoldEnabledArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .versionId(versionId)
                    .build());
        } catch (Exception e) {
            log.error("isObjectLegalHoldEnabled()::is object legal hold enabled error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> getPresignedPostFormData(String bucketName, String objectName, String contentType, Long minSize, Long maxSize) {
        try {
            // Create new post policy for bucketName with 7 days expiry from now.
            PostPolicy policy = new PostPolicy(bucketName, ZonedDateTime.now().plusDays(7));

            // Add condition that 'key' (object name) equals to objectName.
            policy.addEqualsCondition("key", objectName);

            // Add condition that 'Content-Type' starts with contentType.
            policy.addStartsWithCondition("Content-Type", contentType);

            // Add condition that 'content-length-range' is between minSize to maxSize.
            policy.addContentLengthRangeCondition(minSize, maxSize);
            return minioClient.getPresignedPostFormData(policy);
        } catch (Exception e) {
            log.error("getPresignedPostFormData()::get presigned post form data error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectWriteResponse putObject(UploadVo vo) {
        try {
            return minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(vo.getBucketName())
                            .stream(vo.getMultipartFile().getInputStream(), vo.getMultipartFile().getSize(), -1)
                            .contentType(vo.getContentType())
                            .object(vo.getFileStoragePath())
                            .build());
        } catch (Exception e) {
            log.error("putObject()::put object to minIO error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadObject(String bucketName, String objectName, String fileName, String contentType) {
        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(fileName)
                            .contentType(contentType)
                            .build());
        } catch (Exception e) {
            log.error("uploadObject()::upload object to minIO error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadSnowballObjects(String bucketName, List<SnowballObject> obj) {

        /*List<SnowballObject> objects = new ArrayList<SnowballObject>();
        objects.add(
                new SnowballObject(
                        "my-object-one",
                        new ByteArrayInputStream("hello".getBytes(StandardCharsets.UTF_8)),
                        5,
                        null));
        objects.add(
                new SnowballObject(
                        "my-object-two",
                        new ByteArrayInputStream("java".getBytes(StandardCharsets.UTF_8)),
                        4,
                        null));*/
        try {
            minioClient.uploadSnowballObjects(
                    UploadSnowballObjectsArgs.builder()
                            .bucket(bucketName)
                            .objects(obj)
                            .build());
        } catch (Exception e) {
            log.error("uploadSnowballObjects()::upload snowball objects to minIO error");
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean removeObject(String bucketName, String objectName) {
        try {

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            log.error("removeObject()::remove object from minIO error");
            return false;
        }
        return true;
    }

    @Override
    public void removeObject(String bucketName, String objectName, String versionId) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .versionId(versionId)
                            .build());
        } catch (Exception e) {
            log.error("removeObject()::remove object from minIO error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeObject(String bucketName, String objectName, String versionId, boolean bypassGovernanceMode) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .versionId(versionId)
                            .bypassGovernanceMode(bypassGovernanceMode)
                            .build());
        } catch (Exception e) {
            log.error("removeObject()::remove object from minIO error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeObjects(String bucketName, List<DeleteObject> objects) {
        try {
            Iterable<Result<DeleteError>> results =
                    minioClient.removeObjects(
                            RemoveObjectsArgs.builder()
                                    .bucket(bucketName)
                                    .objects(objects)
                                    .build());
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                log.debug("Error in deleting object {}; {}", error.objectName(), error.message());
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public SelectResponseStream selectObjectContent(String bucketName, String objectName, String sqlExpression) {
        try {
            InputSerialization is = new InputSerialization(null, false, null, null, FileHeaderInfo.USE, null, null, null);
            OutputSerialization os = new OutputSerialization(null, null, null, QuoteFields.ASNEEDED, null);
            return minioClient.selectObjectContent(
                    SelectObjectContentArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .sqlExpression(sqlExpression)
                            .inputSerialization(is)
                            .outputSerialization(os)
                            .requestProgress(true)
                            .build());
        } catch (Exception e) {
            log.error("selectObjectContent()::select object content error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setObjectRetention(String bucketName, String objectName, Retention retention, boolean bypassGovernanceMode) {
        try {
            minioClient.setObjectRetention(
                    SetObjectRetentionArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .config(retention)
                            .bypassGovernanceMode(bypassGovernanceMode)
                            .build());
        } catch (Exception e) {
            log.error("setObjectRetention()::set object retention error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setObjectTags(String bucketName, String objectName, Map<String, String> tags) {
        try {
            minioClient.setObjectTags(
                    SetObjectTagsArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .tags(tags)
                            .build());
        } catch (Exception e) {
            log.error("setObjectTags()::set object tags error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public StatObjectResponse statObject(String bucketName, String objectName) {
        try {
            return minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            log.error("statObject()::stat object error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public StatObjectResponse statObject(String bucketName, String objectName, String versionId, ServerSideEncryptionCustomerKey ssec) {
        try {
            return minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .versionId(versionId)
                            .ssec(ssec)
                            .build());
        } catch (Exception e) {
            log.error("statObject()::stat object error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public StatObjectResponse statObject(String bucketName, String objectName, String versionId) {
        try {
            return minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .versionId(versionId)
                            .build());
        } catch (Exception e) {
            log.error("statObject()::stat object error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean makeBucketMo(Buckets bucket) {
        //判空
        if (bucket == null || bucket.getBucketName() == null)
            return false;
        try {
            //先判断是否存在同名bucket,不存在再创建
            boolean bucketExists = this.bucketExists(bucket.getBucketName());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket.getBucketName()).build());
            }
            //存储tags
            if (bucket.getBucketTags() != null && !bucket.getBucketTags().isEmpty())
                minioClient.setBucketTags(SetBucketTagsArgs.builder().bucket(bucket.getBucketName()).tags(bucket.getBucketTags()).build());
            return true;
        } catch (Exception e) {
            log.error("makeBucket()::make bucket error");
            throw new RuntimeException();
        }
    }

    @Override
    public boolean makeBucketsIfNotExist(ArrayList<Buckets> buckets) {
        if (buckets == null || buckets.isEmpty())
            return false;
        for (int i = 0; i < buckets.size(); i++) {
            if (!this.makeBucketMo(buckets.get(i))) {
                log.info("makeBucketsIfNotExist()::make bucket error");
                return false;
            }
        }
        return true;
    }
}
