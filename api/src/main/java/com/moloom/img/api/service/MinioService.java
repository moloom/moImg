package com.moloom.img.api.service;


import com.moloom.img.api.to.Buckets;
import com.moloom.img.api.vo.DownloadVO;
import com.moloom.img.api.vo.UploadVo;
import io.minio.*;
import io.minio.messages.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: moloom
 * @date: 2024-10-28 18:44
 * @description: operations of minio
 */
public interface MinioService {

    /**
     * @param bucketName name of checked
     * @return true if bucket is exist
     * @author moloom
     * @date 2024-10-28 19:38:06
     * @description 检查minio的bucket是否存在
     */
    public boolean bucketExists(String bucketName);

    /**
     * @param bucketName
     * @author moloom
     * @date 2024-12-21 21:44:37
     * @description delete encryption of a bucket
     */
    public void deleteBucketEncryption(String bucketName);

    /**
     * @param bucketName
     * @author moloom
     * @date 2024-12-21 21:45:53
     * @description delete lifecycle of a bucket
     */
    public void deleteBucketLifecycle(String bucketName);

    /**
     * @param bucketName
     * @author moloom
     * @date 2024-12-22 18:20:19
     * @description delete tags of a bucket
     */
    public void deleteBucketTags(String bucketName);

    /**
     * @param bucketName
     * @author moloom
     * @date 2024-12-22 18:21:11
     * @description Deletes bucket policy configuration of a bucket.
     */
    public void deleteBucketPolicy(String bucketName);

    /**
     * @param bucketName
     * @author moloom
     * @date 2024-12-22 18:22:04
     * @description Deletes bucket replication configuration of a bucket.
     */
    public void deleteBucketReplication(String bucketName);

    /**
     * @param bucketName
     * @author moloom
     * @date 2024-12-22 18:30:12
     * @description Deletes bucket notification configuration of a bucket.
     */
    public void deleteBucketNotification(String bucketName);

    /**
     * @param bucketName
     * @author moloom
     * @date 2024-12-22 18:31:37
     * @description Deletes bucket object lock configuration of a bucket.
     */
    public void deleteObjectLockConfiguration(String bucketName);

    /**
     * @param bucketName
     * @return SseConfiguration
     * @author moloom
     * @date 2024-12-22 18:33:45
     * @description Gets encryption configuration of a bucket.
     */
    public SseConfiguration getBucketEncryption(String bucketName);

    /**
     * @param bucketName
     * @return LifecycleConfiguration
     * @author moloom
     * @date 2024-12-22 18:46:37
     * @description Gets lifecycle configuration of a bucket.
     */
    public LifecycleConfiguration getBucketLifecycle(String bucketName);

    /**
     * @param bucketName
     * @return NotificationConfiguration
     * @author moloom
     * @date 2024-12-22 18:47:29
     * @description Gets notification configuration of a bucket.
     */
    public NotificationConfiguration getBucketNotification(String bucketName);

    /**
     * @param bucketName
     * @return String
     * @author moloom
     * @date 2024-12-22 18:48:05
     * @description Gets bucket policy configuration of a bucket.
     */
    public String getBucketPolicy(String bucketName);

    /**
     * @param bucketName
     * @return ReplicationConfiguration
     * @author moloom
     * @date 2024-12-22 18:52:06
     * @description Gets bucket replication configuration of a bucket.
     */
    public ReplicationConfiguration getBucketReplication(String bucketName);

    /**
     * @param bucketName
     * @return Tags
     * @author moloom
     * @date 2024-12-22 18:52:40
     * @description Gets tags of a bucket.
     */
    public Tags getBucketTags(String bucketName);

    /**
     * @param bucketName
     * @return VersioningConfiguration
     * @author moloom
     * @date 2024-12-22 18:53:51
     * @description Gets versioning configuration of a bucket.
     */
    public VersioningConfiguration getBucketVersioning(String bucketName);

    /**
     * @param bucketName
     * @return ObjectLockConfiguration
     * @author moloom
     * @date 2024-12-22 18:56:52
     * @description Gets object-lock configuration in a bucket.
     */
    public ObjectLockConfiguration getObjectLockConfiguration(String bucketName);

    /**
     * @return List of bucket information.
     * @author moloom
     * @date 2024-12-22 18:58:30
     * @description Lists bucket information of all buckets.
     */
    public List<Bucket> listBuckets();

    /**
     * @param extraHeaders
     * @param extraQueryParams
     * @return List of bucket information.
     * @author moloom
     * @date 2024-12-22 19:05:00
     * @description Lists bucket information of all buckets in the specified region.
     */
    public List<Bucket> listBuckets(Map<String, String> extraHeaders, Map<String, String> extraQueryParams);

    /**
     * @param bucketName bucket name
     * @param prefix     prefix of object name
     * @param suffix     suffix of object name
     * @param events     of event
     * @return Lazy closable iterator contains event records.
     * @author moloom
     * @date 2024-12-22 19:05:42
     * @description Listens events of object prefix and suffix of a bucket.</br>
     * The returned closable iterator is lazily evaluated hence its required to iterate to get new records and must be used with try-with-resource to release underneath network resources.
     */
    public CloseableIterator<Result<NotificationRecords>> listenBucketNotification(@NotNull String bucketName, @Nullable String prefix, @Nullable String suffix, String[] events);

    /**
     * @param args the bucket name is not null, other args can be null
     * @return Lazy iterator contains object information.
     * @author moloom
     * @date 2024-12-22 19:33:40
     * @description Lists object information of a bucket.
     */
    public Iterable<Result<Item>> listObjects(ListObjectsArgs args);

    /**
     * @param bucketName to be created
     * @param region
     * @param objectLock
     * @author moloom
     * @date 2024-12-22 20:22:27
     * @description Creates a bucket with given region and object lock feature enabled.
     */
    public void makeBucket(@NotNull String bucketName, @Nullable String region, @Nullable Boolean objectLock);

    /**
     * @param bucketName to be removed
     * @author moloom
     * @date 2024-12-22 20:24:03
     * @description Removes an empty bucket.
     */
    public void removeBucket(String bucketName);

    /**
     * @param args bucket name and encryption configuration
     * @author moloom
     * @date 2024-12-22 20:26:36
     * @description Sets encryption configuration of a bucket.
     */
    public void setBucketEncryption(SetBucketEncryptionArgs args);

    /**
     * @param args bucket name and lifecycle configuration
     * @author moloom
     * @date 2024-12-22 20:28:33
     * @description Sets lifecycle configuration to a bucket.
     */
    public void setBucketLifecycle(SetBucketLifecycleArgs args);

    /**
     * @param args bucket name and notification configuration
     * @author moloom
     * @date 2024-12-22 20:30:46
     * @description Sets notification configuration to a bucket.
     */
    public void setBucketNotification(SetBucketNotificationArgs args);

    /**
     * @param args bucket name and policy
     * @author moloom
     * @date 2024-12-22 20:32:46
     * @description Sets bucket policy configuration to a bucket.
     */
    public void setBucketPolicy(SetBucketPolicyArgs args);

    /**
     * @param args bucket name and replication configuration
     * @author moloom
     * @date 2024-12-22 20:33:22
     * @description Sets bucket replication configuration to a bucket.
     */
    public void setBucketReplication(SetBucketReplicationArgs args);

    /**
     * @param args bucket name and tags
     * @author moloom
     * @date 2024-12-22 20:33:52
     * @description Sets tags to a bucket.
     */
    public void setBucketTags(Buckets args);

    /**
     * @param args bucket name and versioning configuration
     * @author moloom
     * @date 2024-12-22 20:35:24
     * @description Sets versioning configuration of a bucket.
     */
    public void setBucketVersioning(SetBucketVersioningArgs args);

    /**
     * @param args bucket name and object lock configuration
     * @author moloom
     * @date 2024-12-22 20:37:11
     * @description Sets object-lock configuration in a bucket.
     */
    public void setObjectLockConfiguration(SetObjectLockConfigurationArgs args);

    /**
     * @param args
     * @return Contains information of created object.
     * @author moloom
     * @date 2024-12-22 20:42:47
     * @description Creates an object by combining data from different source objects using server-side copy.
     */
    public ObjectWriteResponse composeObject(ComposeObjectArgs args);

    /**
     * @param args
     * @return Contains information of created object.
     * @author moloom
     * @date 2024-12-22 20:50:45
     * @description Creates an object by server-side copying data from another object.
     */
    public ObjectWriteResponse copyObject(CopyObjectArgs args);

    /**
     * @param bucketName bucket name
     * @param objectName the name of the tag object to be deleted.
     * @author moloom
     * @date 2024-12-22 20:53:00
     * @description Deletes tags of an object.
     */
    public void deleteObjectTags(String bucketName, String objectName);

    /**
     * @param bucketName bucket name
     * @param objectName object name
     * @author moloom
     * @date 2024-12-22 23:41:57
     * @description Disables legal hold on an object.
     */
    public void disableObjectLegalHold(String bucketName, String objectName);

    /**
     * @param bucketName bucket name
     * @param objectName object name
     * @author moloom
     * @date 2024-12-22 23:43:02
     * @description Enables legal hold on an object.
     */
    public void enableObjectLegalHold(String bucketName, String objectName);

    /**
     * @param vo info about download
     * @return return InputStream,and must be closed after use to release network resources.
     * @author moloom
     * @date 2024-10-31 01:35:09
     * @description Gets data of an object.
     */
    public InputStream getObject(DownloadVO vo);

    /**
     * @param bucketName      bucket name
     * @param objectName      object name
     * @param destinationPath destination path
     * @author moloom
     * @date 2024-12-22 23:48:54
     * @description Downloads data of an object to file.
     */
    public void downloadObject(@NotNull String bucketName, @NotNull String objectName, @NotNull String destinationPath, @Nullable ServerSideEncryptionCustomerKey ssec);

    /**
     * @param bucketName bucket name
     * @param objectName object name
     * @param versionId  object version
     * @return Object retention configuration.
     * @author moloom
     * @date 2024-12-22 23:57:30
     * @description Gets retention configuration of an object.
     */
    public Retention getObjectRetention(@NotNull String bucketName, @NotNull String objectName, @NotNull String versionId);

    /**
     * @param bucketName bucket name
     * @param objectName object name
     * @return tags
     * @author moloom
     * @date 2024-12-23 00:00:59
     * @description Gets tags of an object.
     */
    public Tags getObjectTags(String bucketName, String objectName);

    /**
     * @param args
     * @return URL String
     * @author moloom
     * @date 2024-12-24 15:09:48
     * @description Gets presigned URL of an object for HTTP method, expiry time and custom request parameters.
     */
    public String getPresignedObjectUrl(GetPresignedObjectUrlArgs args);

    /**
     * @param bucketName
     * @param objectName
     * @param versionId
     * @return true if legal hold is enabled on an object; otherwise false
     * @author moloom
     * @date 2024-12-24 15:14:17
     * @description Returns true if legal hold is enabled on an object.
     */
    public boolean isObjectLegalHoldEnabled(@NotNull String bucketName, @NotNull String objectName, @NotNull String versionId);

    /**
     * @param bucketName
     * @param objectName
     * @param contentType the content type of the object
     * @param minSize     the minimum size of the object
     * @param maxSize     the maximum size of the object
     * @return Contains form-data to upload an object using POST method.
     * @author moloom
     * @date 2024-12-24 15:49:09
     * @description Gets form-data of PostPolicy of an object to upload its data using POST method.
     */
    public Map<String, String> getPresignedPostFormData(String bucketName, String objectName, String contentType, Long minSize, Long maxSize);

    /**
     * @param vo 存储的img和img信息
     * @return
     * @author moloom
     * @date 2024-10-31 01:30:22
     * @description Uploads given stream as object in bucket.
     */
    public ObjectWriteResponse putObject(UploadVo vo);

    /**
     * @param bucketName  bucket name
     * @param objectName  object name
     * @param fileName    The name of the file to be uploaded (with the full path)
     * @param contentType the content type of the file
     * @author moloom
     * @date 2024-12-24 16:00:36
     * @description Uploads contents from a file as object in bucket.
     */
    public void uploadObject(String bucketName, String objectName, String fileName, String contentType);

    /**
     * @param bucketName
     * @param obj        The list objects to be uploaded with its objectName
     * @author moloom
     * @date 2024-12-24 16:11:13
     * @description Uploads multiple objects in a single put call. </br>
     * It is done by creating intermediate TAR file optionally compressed which is uploaded to S3 service.
     */
    public void uploadSnowballObjects(String bucketName, List<SnowballObject> obj);


    /**
     * @param bucketName
     * @param objectName
     * @author moloom
     * @date 2024-12-12 21:06:12
     * @description Removes an object
     */
    public boolean removeObject(String bucketName, String objectName);

    /**
     * @param bucketName
     * @param objectName
     * @param versionId
     * @author moloom
     * @date 2024-12-24 16:18:04
     * @description removes an object with versionId
     */
    public void removeObject(String bucketName, String objectName, String versionId);

    /**
     * @param bucketName
     * @param objectName
     * @param versionId
     * @param bypassGovernanceMode
     * @author moloom
     * @date 2024-12-24 16:18:57
     * @description removes an object with versionId and bypassRetentionMode
     */
    public void removeObject(String bucketName, String objectName, String versionId, boolean bypassGovernanceMode);

    /**
     * @param bucketName
     * @param objects    the objects to be removed
     * @author moloom
     * @date 2024-12-12 21:10:07
     * @description Removes multiple objects
     */
    public boolean removeObjects(String bucketName, List<DeleteObject> objects);

    /**
     * @param bucketName
     * @param objectName
     * @param sqlExpression SQL expression to filter the content of the object.
     * @return Contains filtered records and progress.
     * @author moloom
     * @date 2024-12-24 16:43:09
     * @description Selects content of an object by SQL expression.
     */
    public SelectResponseStream selectObjectContent(String bucketName, String objectName, String sqlExpression);

    /**
     * @param bucketName
     * @param objectName
     * @param retention            retention configuration
     * @param bypassGovernanceMode
     * @author moloom
     * @date 2024-12-24 17:07:24
     * @description Sets retention configuration to an object.
     */
    public void setObjectRetention(String bucketName, String objectName, Retention retention, boolean bypassGovernanceMode);

    /**
     * @param bucketName
     * @param objectName
     * @param tags       the tags to be set
     * @author moloom
     * @date 2024-12-24 17:08:35
     * @description Sets tags to an object.
     */
    public void setObjectTags(String bucketName, String objectName, Map<String, String> tags);

    /**
     * @param bucketName
     * @param objectName
     * @return Populated object information and metadata.
     * @author moloom
     * @date 2024-12-24 17:11:32
     * @description Gets object information and metadata of an object.
     */
    public StatObjectResponse statObject(String bucketName, String objectName);

    /**
     * @param bucketName
     * @param objectName
     * @param versionId
     * @return Populated object information and metadata.
     * @author moloom
     * @date 2024-12-24 17:18:30
     * @description Gets object information and metadata of an object with versionId.
     */
    public StatObjectResponse statObject(String bucketName, String objectName, String versionId);

    /**
     * @param bucketName
     * @param objectName
     * @param versionId
     * @param ssec       SSE-C encrypted
     * @return Populated object information and metadata.
     * @author moloom
     * @date 2024-12-24 17:21:36
     * @description Gets object information and metadata of an object with versionId and server-side encryption.
     */
    public StatObjectResponse statObject(String bucketName, String objectName, String versionId, ServerSideEncryptionCustomerKey ssec);

    /**
     * @param buckets 创建的bucket信息
     * @return true if the bucket make successfully; otherwise false
     * @author moloom
     * @date 2024-10-28 21:43:48
     * @description 创建 bucket
     */
    public boolean makeBucketMo(Buckets buckets);

    /**
     * @param buckets
     * @return true if make all successful
     * @author moloom
     * @date 2024-10-28 21:49:14
     * @description 若bucket不存在，则创建bucket
     */
    public boolean makeBucketsIfNotExist(ArrayList<Buckets> buckets);


}
