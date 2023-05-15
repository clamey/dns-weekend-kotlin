import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import kotlin.random.Random

const val DNS_SERVER_PORT = 53

const val TYPE_A = 1
const val CLASS_IN = 1

fun main(args: Array<String>) {
    val query = buildQuery("www.example.com", TYPE_A)

    println("Sending: ${query.size} bytes as ${query.toHex()}")

    val socket = DatagramSocket()
    socket.send(DatagramPacket(query, query.size, InetAddress.getByName("1.1.1.1"), DNS_SERVER_PORT))
}

fun buildQuery(domain: String, recordType: Int): ByteArray =
    DNSHeader(
        id = Random.nextInt(),
        flags = bitset {
            set(8)
        }
    ).toByteArray() + DNSQuestion(
        name = domain,
        type_ = recordType,
        class_ = CLASS_IN
    ).toByteArray()