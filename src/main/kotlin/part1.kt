import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.util.BitSet
import kotlin.random.Random

data class DNSHeader(
    val id: Int = Random.nextInt(),
    val flags: BitSet = BitSet(16),
    val numQuestions: Int = 1,
    val numAnswers: Int = 0,
    val numAuthorities: Int = 0,
    val numAdditionals: Int = 0
) {
    init {
        flags.set(8)
    }

    fun toByteArray(): ByteArray {
        val boas = ByteArrayOutputStream()

        DataOutputStream(boas).use {
            it.writeShort(id)
            it.write(flags.toByteArray())
            it.writeShort(numQuestions)
            it.writeShort(numAnswers)
            it.writeShort(numAuthorities)
            it.writeShort(numAdditionals)
        }

        return boas.toByteArray()
    }
}

data class DNSQuestion(
    val qName: String,
    val qType: Int = 1,
    val qClass: Int = 1) {

    fun toByteArray(): ByteArray {
        val boas = ByteArrayOutputStream()
        DataOutputStream(boas).use { dos ->
            qName.split('.').map {
                it.toByteArray()
            }.forEach {
                dos.writeByte(it.size)
                dos.write(it)
            }
            dos.writeByte(0);
            dos.writeShort(qType)
            dos.writeShort(qClass)
        }
        return boas.toByteArray()
    }
}


fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }