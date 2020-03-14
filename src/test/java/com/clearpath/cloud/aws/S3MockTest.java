package com.clearpath.cloud.aws;

import com.adobe.testing.s3mock.junit4.S3MockRule;
import com.adobe.testing.s3mock.util.HashUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class S3MockTest {


    @ClassRule
    public static final S3MockRule S3_MOCK_RULE = S3MockRule.builder().silent().build();

    private static final String BUCKET_NAME = "mydemotestbucket";
    private static final String UPLOAD_FILE_NAME = "src/test/resources/sampleFile.txt";

    private final AmazonS3 s3Client = S3_MOCK_RULE.createS3Client();

    /**
     * Creates a bucket, stores a file, downloads the file again and compares checksums.
     *
     * @throws Exception if FileStreams can not be read
     */
    @Test
    public void testUpload() throws Exception {
        final File uploadFile = new File(UPLOAD_FILE_NAME);

        s3Client.createBucket(BUCKET_NAME);
        PutObjectResult putObjectResult =
                s3Client.putObject(new PutObjectRequest(BUCKET_NAME, uploadFile.getName(), uploadFile));

        assertThat(putObjectResult, notNullValue());

        final S3Object s3Object = s3Client.getObject(BUCKET_NAME, uploadFile.getName());

        final InputStream uploadFileIs = new FileInputStream(uploadFile);
        final String uploadHash = HashUtil.getDigest(uploadFileIs);
        final String downloadedHash = HashUtil.getDigest(s3Object.getObjectContent());
        uploadFileIs.close();
        s3Object.close();

        assertThat("Up- and downloaded Files should have equal Hashes", uploadHash, is(equalTo(downloadedHash)));
    }

}
