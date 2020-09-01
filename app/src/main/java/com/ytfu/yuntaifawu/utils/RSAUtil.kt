package com.ytfu.yuntaifawu.utils

import com.ytfu.yuntaifawu.BuildConfig
import org.apaches.commons.codec.binary.Base64
import java.security.GeneralSecurityException
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**RSA加密*/
fun rsaEncrypt(str: String, publicKey: String): String {
    return try {
        //base64编码的公钥ipher.getInstance(“RSA/ECB/PKCS1Padding”);
        val decoded = Base64.decodeBase64(publicKey)
        val pubKey: RSAPublicKey = KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(decoded)) as RSAPublicKey
        //RSA加密
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        Base64.encodeBase64String(cipher.doFinal(str.toByteArray(charset("UTF-8"))))
    } catch (e: GeneralSecurityException) {
        if (BuildConfig.IS_DEBUG) {
            e.printStackTrace()
        }
        ""
    }
}

/**RSA解密*/
fun rsaDecrypt(str: String, privateKey: String): String {
    return try {
        //64位解码加密后的字符串
        val inputByte = Base64.decodeBase64(str.toByteArray(charset("UTF-8")))
        //base64编码的私钥
        val decoded = Base64.decodeBase64(privateKey)
        val priKey = KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(decoded)) as RSAPrivateKey
        //RSA解密
        val cipher: Cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, priKey)
        String(cipher.doFinal(inputByte))
    } catch (e: GeneralSecurityException) {
        ""
    }
}
//
//fun main() {
//    val privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCEfN0sq3DFRqlMk5wJXg/AC2wL0Deu4vQ4kx8jHFexCmbWofTQnr3QtIKyulsRUK8PPTu29TfMxTAHwvx4GLqlCk8cwEo1JHsfXlxZaPmhP59wtAxLk2rDm+XAXRpypSgYt9qnUwaXhMP2FX88OWC1C3bueOS9ybzj7uIcMdlOq1KAcoUoNAEeklEqHwc/VX0VtHp2uZy/bPGw4qHxbMYXKIk80blHr6WWiNH3bl6ohx/3KfqBiqiZgZM51Ez2M4rI8F+CT7wAm3Nh7ux4ZsxsgVpE30uKylBSwG0iSYumWlyEgeTEWOEStfwEgppL3Gx5kkC9ofU0sigQpN0omTSJAgMBAAECggEAFqr6eKMYl1hArS5wOxYR4czSfq7waAbCvE9Hu40k39CkycJKDcurNGM+HXBPFoZUfWn/po0MMAfo8NsGsVGh1Y0O/h9UUwVb34EYkd+pHlKxZ+oWVHPzh2ZMB/6mAMmLM7d5PLmy3gfEM314GIjEqBU/EwwyLtqua906aJKK2K+uUMQGpBwPvJ6LPtbjW7m94rQdrO8zeD2Khhi+BRXnwphHak18Vl0L4stLtTiZMnn7aF0v/ke5cu1Nf2ljN2MMhUusOgAFZNlW2JSZ1MM9ePdilqYAEJb73bnDrjYgZI1IaczGGJGG/kxcV+KUgT+tCkSOARNUdgBtDdlnRXZYhQKBgQC9rk0R8ai/LEoeW/IlRP/teahkaBjKjJWUOlRmEuAbd53aB6w/QTKyT+GJAUfhwconqkWrjlTQeS6Ah1FYEpGWt9mTXb+fmolm80OVJAYqsIThdC0WrmLf1aKxxxCL1fGuZ2/r8rOQfxAKwEKIjshBwSHuGubq02OKyGcJRwDYjwKBgQCyz2YY3n4ztRbpAgsicAYehOtz2CJgMfNcYCE82uAds3jUovbL1Ls6AktbfPsrKnj7oC/wK1t5rxE+GtmnLcfPBw/eKeZPeUzMfEn7XAYr4LlWUT31m0o6hhL9/+G0NNNSpvCv74ooArvp6NaPW1BXg/XaX03aJ3rKV/YdJdg9ZwKBgQCpKRxEp3811zrWrmtaf5m64OejfA63/P6dLhP84FKLTHczbepYkz2yNnlOJR1FIf22uCzgHdRYA+rL52JNKGo8vwPEYDQ4E6r8bHDWPkVdpwUqx+A6PRWix7UL1RtVily6jDcNdupbMGZPID116diAg8xNvCI6RIGe0LDLXk5L0wKBgGlzCBnYdIKX4/fr3Zcki2dKKfS6XKNwMb2jb1aNGahKZBK6PGM+hF+UZM6CaTkupgR2lm7DkwGy7a87dGo0BGaiGoiBGzJ5LYJWNecHgRg1QfeWl7yVJPRYhzDFy1xT3uAaVUzcLhSkcB9k0cvfJQVHbCCpHaduqupTb3AHFnw3AoGASrrtIqa8vFzmzJH72TUhAlSbGVj7qQYNZGsCvVhIkDKQOk8UzOJKu2CrOCRjbcV0v3veNbBlUybQBy5MZluC7sKsp34hmKt5hLslEZoZ0hpFNBnV2ga2EZInziY/VYUM4U0AzT0MibOlzmgDTf9NcCE0Sd4WPuOhE5lpToXEz4A="
//    val publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhHzdLKtwxUapTJOcCV4PwAtsC9A3ruL0OJMfIxxXsQpm1qH00J690LSCsrpbEVCvDz07tvU3zMUwB8L8eBi6pQpPHMBKNSR7H15cWWj5oT+fcLQMS5Nqw5vlwF0acqUoGLfap1MGl4TD9hV/PDlgtQt27njkvcm84+7iHDHZTqtSgHKFKDQBHpJRKh8HP1V9FbR6drmcv2zxsOKh8WzGFyiJPNG5R6+llojR925eqIcf9yn6gYqomYGTOdRM9jOKyPBfgk+8AJtzYe7seGbMbIFaRN9LispQUsBtIkmLplpchIHkxFjhErX8BIKaS9xseZJAvaH1NLIoEKTdKJk0iQIDAQAB";
//
//    //    val os = "1234"
//    //    val encode = "G1HBO8Kf2HwKH+vx5sUJ2sYosqByct/xzgoxT3Xk3cc/SdNeEc/pDkuEZb2CmF7tfAHSda4agS7IXwuwhf9ILylDV34mKXtlpJTlwyi4jcRIriahQZF2WWhrofso+L+MOOEP4VlrWSEyap7uoUaXgYk9+QZUtzNEVWet2rlTi1xT/yT1WS2oLokQrmhq39ggkffMdOFnpxazmxstB4LTwIV5dtrdKW5jyTzrrL4q1SdSeobmdDN9ruqf+9sOF9dqVbp68tHPReEgfbNECbA2Jsm0cUVUFZysJKe7pKnD9mbko7Kbq2VoWVsAbD+QYPUjWSKkmxhXhvrZ5oPT/UQ1Og=="
//    //    val encod1 = "G1HBO8Kf2HwKH+vx5sUJ2sYosqByct/xzgoxT3Xk3cc/SdNeEc/pDkuEZb2CmF7tfAHSda4agS7IXwuwhf9ILylDV34mKXtlpJTlwyi4jcRIriahQZF2WWhrofso+L+MOOEP4VlrWSEyap7uoUaXgYk9+QZUtzNEVWet2rlTi1xT/yT1WS2oLokQrmhq39ggkffMdOFnpxazmxstB4LTwIV5dtrdKW5jyTzrrL4q1SdSeobmdDN9ruqf+9sOF9dqVbp68tHPReEgfbNECbA2Jsm0cUVUFZysJKe7pKnD9mbko7Kbq2VoWVsAbD+QYPUjWSKkmxhXhvrZ5oPT/UQ1Og=="
//    val encode = "DrLveH+aNO2LYd6v8UvCGGvSxofLamCmRcOLGRxv0ak614vaBPLgNRjpC7wjLJY13pcUT4RH+JyZSiJh6/2T+kxew6Zc4VmS9kJ7gTBIDwZ4IGjqF+GeaynZbF+pV52Joh+TFB2pMWZZ9PMTYBB96lKa4ooPDTpmN72ImTfeIwRrmEjo+KUG/+xlu5PTFOtgyE83nC/dIcxy2ZSm35hntOpu0U6SecwWuwXK2frwUyZueFTimfHlejDZ1klfk7tH0ua655cU+u6tGfmhDONwxZbojrx6EyDpYqwlFuGV3nYDEHUEhzISXSPo0bKe1MbHq+S/02iVKo0YP22KKFdbXA==" //rsaEncrypt("77a5c15d49cca93aa6d3ffa828361066,4287", publicKey)
//
//    println("加密：$encode")
//    val decode = rsaDecrypt(encode, privateKey)
//    println("解密：$decode")
//
//
//}
