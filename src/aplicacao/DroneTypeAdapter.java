package aplicacao;

import com.google.gson.*;
import dados.Drone;
import dados.DronePessoal;
import dados.DroneCargaInanimada;
import dados.DroneCargaViva;

import java.lang.reflect.Type;

public class DroneTypeAdapter implements JsonDeserializer<Drone>, JsonSerializer<Drone> {

    @Override
    public Drone deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        switch (type) {
            case "DronePessoal":
                return context.deserialize(json, DronePessoal.class);
            case "DroneCargaInanimada":
                return context.deserialize(json, DroneCargaInanimada.class);
            case "DroneCargaViva":
                return context.deserialize(json, DroneCargaViva.class);
            default:
                throw new JsonParseException("Tipo de drone desconhecido: " + type);
        }
    }


    @Override
    public JsonElement serialize(Drone src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = context.serialize(src).getAsJsonObject();
        jsonObject.addProperty("type", src.getClass().getSimpleName()); // Adiciona o campo "type"
        return jsonObject;
    }
}
