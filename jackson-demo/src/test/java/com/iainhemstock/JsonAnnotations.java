package com.iainhemstock;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***************************************************************************************************
 * @JsonIgnore
 * *************************************************************************************************
 * This property-level annotation tells Jackson to ignore this class member when attempting to match
 * fields from the json source to this object. If there was no date field in the json source then an
 * exception would be thrown if this annotation was not used.
 *
 * Therefore Jackson will only attempt to map to the id member in this example.
 *
 * To ignore multiple properties consider using @JsonIgnoreProperties rather than annotating each
 * property to be ignored.
 */
class JsonIgnoreAnnotation {
    private int id;

    @JsonIgnore
    private Date date;
}

/***************************************************************************************************
 * @JsonIgnoreProperties
 * *************************************************************************************************
 * To ignore multiple properties they can be specified at the class level with @JsonIgnoreProperties.
 * Jackson will only attempt to map to the id member in this example.
 */
@JsonIgnoreProperties({"date", "size"})
class JsonIgnorePropertiesAnnotation {
    private int id;
    private Date date;
    private int size;
}

/***************************************************************************************************
 * @JsonSetter
 * *************************************************************************************************
 * This is a useful annotation to apply to a setter method when the json source field is named
 * differently to the class member. For example the json source might have a field called 'id' but
 * the class member we wish to map it to is called 'personId'. This annotation tells Jackson to map
 * the 'id' field to the 'personId' member.
 */
class JsonSetterAnnotation {
    private int personId;

    @JsonSetter("id")
    public void setPersonId(int personId) { this.personId = personId; }
}

/***************************************************************************************************
 * @JsonAnySetter
 * *************************************************************************************************
 * It could be useful to have a method that Jackson uses to map unrecognized fields to. Unrecognized
 * fields are fields that Jackson can't find a corresponding member in a class for. If a method is
 * annotated with @JsonAnySetter then Jackson will use that method instead of throwing an exception.
 *
 * For example, if the json source was:
 *
 *          {
 *              "id" : 123,
 *              "name : "Bob"
 *          }
 *
 * then Jackson will call set() for both unrecognized fields despite the fact that is no id or name
 * member of the class.
 */
class JsonAnySetterAnnotation {
    private Map<String, Object> map = new HashMap<>();

    @JsonAnySetter
    public void set(String fieldName, Object value) {
        this.map.put(fieldName, value);
    }
}

/***************************************************************************************************
 * @JsonCreater and @JsonProperty
 * *************************************************************************************************
 * @JsonCreater is a constructor level annotation and is used to tell Jackson that this constructor
 * should be used to map the json fields to the class members. This is necessary for immutable objects
 * since they don't have setter methods and are typically initialized through their constructor anyway.
 *
 * Constructor parameters are annotated with @JsonProperty so Jackson knows which member to map which
 * json field to. The value in the @JsonProperty annotation is the field in the json source.
 */
class JsonCreatorAnnotation {
    private int id;
    private String name;

    @JsonCreator
    JsonCreatorAnnotation(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
}

/***************************************************************************************************
 * @JsonInclude
 * *************************************************************************************************
 * This class level annotation tells Jackson only to serialize members if the specified condition is met.
 * Available conditions are:
 *          JsonInclude.Include.ALWAYS
 *          JsonInclude.Include.CUSTOM
 *          JsonInclude.Include.NON_ABSENT
 *          JsonInclude.Include.NON_DEFAULT
 *          JsonInclude.Include.NON_EMPTY
 *          JsonInclude.Include.NON_NULL
 *          JsonInclude.Include.USE_DEFAULTS
 *
 * In the following example, the serialized output would ignore the name member as it is null:
 *
 *          {
 *              "size" : 0
 *          }
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class JsonIncludeAnnotation {
    private int size = 0;
    private String name = null;
}

/***************************************************************************************************
 * @JsonGetter
 * *************************************************************************************************
 * This method-level annotation tells Jackson not to use a member name when serializing the object
 * but to use the argument to the annotated getter method instead.
 *
 * In this example the field in the serialized object would be named 'mountainId' and not 'id':
 *
 *          {
 *              "mountainId" : <some value>
 *          }
 */
class JsonGetterAnnotation {
    private int id;

    @JsonGetter("mountainId")
    public int getId() {
        return this.id;
    }
}

/***************************************************************************************************
 * @JsonAnyGetter
 * *************************************************************************************************
 * This class-level annotation tells Jackson to serialize the contents of a map as individual fields.
 * For example if the map contained the following key/value pairs:
 *
 *              map.put("latitude", "-37.3159"),
 *              map.put("longitude", "81.1496")
 *
 * then the serialized object output would be as follows:
 *
 *              {
 *                  "id" : 0,
 *                  "latitude" : "-37.3159",
 *                  "longitude" : "81.1496"
 *              }
 */
class JsonAnyGetterAnnotation {
    private int id = 0;
    private Map<String, Object> map = new HashMap<>();

    public int getId() {
        return this.id;
    }

    @JsonAnyGetter
    public Map<String, Object> getMap() {
        return this.map;
    }
}

/***************************************************************************************************
 * @JsonPropertyOrder
 * *************************************************************************************************
 * This class-level annotation tells Jackson in which order to serialize the properties. Without this
 * annotation the properties are serialized in the order in which they are found in the class.
 *
 * This example would produce the following output when serialized:
 *
 *              {
 *                  "name" : <some value>,
 *                  "email" : <some value>,
 *                  "id" : <some value>
 *              }
 */
@JsonPropertyOrder({"name", "email", "id"})
class JsonPropertyOrderAnnotation {
    private int id;
    private String name;
    private String email;
}

/***************************************************************************************************
 * @JsonValue
 * *************************************************************************************************
 * This method-level annotation tells Jackson not to serialize the object by itself because we are
 * going to specify how to serialize it ourselves in the annotated method instead.
 *
 * This example produces the following output when serialized:
 *
 *              "id:0"
 */
class JsonValueAnnotation {
    private int id = 0;

    @JsonValue
    public String toJson() {
        return String.format("id:%d", id);
    }
}

