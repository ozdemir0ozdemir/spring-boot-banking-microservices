package ozdemir0zdemir.userservice.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ozdemir0zdemir.userservice.domain.Gender;

import java.io.IOException;

public class GenderPatternDeserializer extends StdDeserializer<Gender> {

    public GenderPatternDeserializer() {
        this(null);
    }

    protected GenderPatternDeserializer(Class<Gender> vc) {
        super(vc);
    }

    @Override
    public Gender deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String genderName = node.asText();

        Gender gender;
        try {
            gender = Gender.valueOf(genderName);
        }
        catch (IllegalArgumentException ex) {
            gender = Gender.UNKNOWN;
        }

        return gender;
    }
}
