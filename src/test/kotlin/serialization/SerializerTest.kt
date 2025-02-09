package ru.yole.jkid.serialization

import org.junit.Test
import ru.yole.jkid.JsonExclude
import ru.yole.jkid.JsonName
import ru.yole.jkid.deserialization.*
import kotlin.test.assertEquals

class SerializerTest {
    @Test fun testSimple() {
        val result = serialize(SingleStringProp("x"))
        assertEquals("""{"s": "x"}""", result)
    }

    @Test fun testTwoInts() {
        val result = serialize(TwoIntProp(1, 2))
        assertEquals("""{"i1": 1, "i2": 2}""", result)
    }

    @Test fun testTwoBools() {
        val result = serialize(TwoBoolProp(true, false))
        assertEquals("""{"b1": true, "b2": false}""", result)
    }

    @Test fun testObject() {
        val result = serialize(SingleObjectProp(SingleStringProp("x")))
        assertEquals("""{"o": {"s": "x"}}""", result)
    }

    @Test fun testList() {
        val result = serialize(SingleListProp(listOf("a", "b")))
        assertEquals("""{"o": ["a", "b"]}""", result)
    }

    @Test fun testListOfNull() {
        val result = serialize(SingleListProp(listOf(null, "b")))
        assertEquals("""{"o": [null, "b"]}""", result)
    }

    @Test fun testJsonName() {
        val result = serialize(SingleAnnotatedStringProp("x"))
        assertEquals("""{"q": "x"}""", result)
    }

    @Test fun testCustomSerializer() {
        val result = serialize(SingleCustomSerializedProp(1))
        assertEquals("""{"x": "ONE"}""", result)
    }

    @Test fun testEscapeSequences() {
        val result = serialize(SingleStringProp("\\\""))
        assertEquals("""{"s": "\\\""}""", result)
    }

    @Test fun testJsonExclude() {
        val result = serialize(TwoPropsOneExcluded("foo", "bar"))
        assertEquals("""{"s": "foo"}""", result)
    }

    @Test fun testStudent() {
        val result = serialize(Student(1, "hello", listOf("Math", "Computer")))
        assertEquals("""{"major": ["Math", "Computer"], "name": "hello", "studentId": 1}""", result)
    }

    @Test fun testStudentVer2() {
        val result = serialize(StudentVer2(1, "hello", listOf("Math", "Computer"), false))
        assertEquals("""{"major": ["Math", "Computer"], "name": "hello", "student identifier": 1}""", result)
    }
}

data class Student(
    val studentId: Long,
    val name: String,
    val major: List<String>
)

data class StudentVer2(
    @JsonName(name = "student identifier") val studentId: Long,
    val name: String,
    val major: List<String>,
    @JsonExclude val isRegister: Boolean
)