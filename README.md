# dns-weekend-kotlin

Kotlin version of the excellent https://implement-dns.wizardzines.com

## Part 1

### Part 1.1

1. Using Kotlin `data` classes for the `DNSHeader` and `DNSQuestion`
2. The member variable names were changed to CamelCase rather than have underbars separating words 
3. The `DNSHeader.flags` variable in Kotlin is a `BitSet` rather than an Int, because that field is 16 bit with an odd format and BitSet is easy to work with in Kotlin
4. The `DNSQuestion.name` variable is a String rather than array of bytes, as Strings are easy to work with and convert to bytes in Kotlin

### Part 1.2

1. Rather than the standalone functions `header_to_bytes` and `question_to_bytes`, the data classes each have a `toByteArray()` function that knows how to write out the object to the needed binary format.
2. Since Kotlin doesn't have the equivalent of a `struct.pack()`, the `toByteArray()` in each class uses a `DataOutputStream` to write itself into an array of bytes.  It's similar in that the class's variables are `Int` types, which are 32 bits in Kotlin, but are written to the byte array as 16 bit/2 byte Shorts.
3. Each `DataOutputStream` writes each field in the class to a backing `ByteArrayOutputStream`.
4. The `DataOutputStream` uses the `.use {}` Kotlin lambda, which will do a try-with-resources on the output stream.
5. All byte arrays in the JVM are "big endian" by default, so we don't need to specify anything for that.

### Part 1.3

1. The `DNSQuestion` name is encoded using a Kotlin function chain.  First the `name` is chopped up in into a list using the `split` function on the `.` character.  Then each element from the `split` uses the `map` function to transform from a `String` to a `ByteArray`.  Then a `forEach` function is used for each now `ByteArray` element to write the length of the element and then the value of the element to the `DataOutputStream`.
2. After the name is processed, then a `0` is written as a delimiter, and then the type and class fields.  Again, these are `Int` fields on the classes getting written as `Short` types to the output stream, which is similar to the "H" in the `string.pack("!HH", 3, 34)` approach.

### Part 1.4

1. Since the `DNSQuestion.name` encoding is part of the `toByteArray()` function, it's not called from the `buildQuery()` function.
2. The constructors of the classes provide sensible defaults, like `DNSHeader.name` but they are overriden in the `buildQuery()` function for example's sake.
3. The `BitSet` class used for the `DNSHeader.flags` variable is from Java, so to make it more usable in Kotlin, the `bitset` typesafe builder is used to initialize a `BitSet` and set the 8th bit to true.
4. The `+` operator is used to concatenate the byte array representations of the `DNSHeader` and `DNSQuestion` instances.

### Part 1.5

1. In order to see the query as a hex string, a Kotlin helper function called `toHex()` is added to the `ByteArray` class.