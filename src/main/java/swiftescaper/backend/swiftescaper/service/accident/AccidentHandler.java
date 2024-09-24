package swiftescaper.backend.swiftescaper.service.accident;

import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentRequestDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccidentHandler {
    private static final Map<String, String> directionMap = new HashMap<>();

    /**
     * 지침 Set
     */
    static {
        // type: 1 (홍수) 에 대한 지침
        directionMap.put("1-S-near", "침수 위험이 있으니 진입을 멈추고, 우회하세요.");
        directionMap.put("1-S-mid", "침수 위험이 있으니 빠르게 벗어나세요");
        directionMap.put("1-S-far", "침수 위험이 있으니 빠르게 벗어나세요");
        directionMap.put("1-M-near", "차량 외부로 탈출 후 높은 곳으로 이동하세요");
        directionMap.put("1-M-mid", "엔진을 끄고 외부로 대비하세요");
        directionMap.put("1-M-far", "엔진을 끄고 외부로 대비하세요");
        directionMap.put("1-L-near", "차량 외부로 탈출 후 높은 곳으로 이동하세요");
        directionMap.put("1-L-mid", "차량 외부로 탈출 후 높은 곳으로 이동하세요");
        directionMap.put("1-L-far", "엔진을 끄고 외부로 대비하세요");

        directionMap.put("1-out", "침수 위험이 있으니 진입을 멈추고, 우회하세요.");

        // type: 2 (추돌) 에 대한 지침
        directionMap.put("2-S-near", "속도를 줄이고, 비상등을 키세요");
        directionMap.put("2-S-mid", "속도를 줄이고, 비상등을 키세요");
        directionMap.put("2-S-far", "속도를 줄이고, 비상등을 키세요");
        directionMap.put("2-M-near", "속도를 줄이고, 비상등을 키세요. 화재의 위험성이 있어요");
        directionMap.put("2-M-mid", "속도를 줄이고, 비상등을 키세요. 화재의 위험성이 있어요");
        directionMap.put("2-M-far", "속도를 줄이고, 비상등을 키세요. 화재의 위험성이 있어요");
        directionMap.put("2-L-near", "엔진을 멈추고 외부로 탈출하세요");
        directionMap.put("2-L-mid", "속도를 줄이고, 비상등을 키세요. 화재의 위험성이 있어요");
        directionMap.put("2-L-far", "속도를 줄이고, 비상등을 키세요. 화재의 위험성이 있어요.");

        directionMap.put("2-out", "터널 내로 진입을 멈추고, 우회하세요.");

        // type: 3 (화재) 에 대한 지침
        directionMap.put("3-S-near", "엔진을 끄고 소화기 사용을 준비하세요");
        directionMap.put("3-S-mid", "엔진을 끄고 대피한 후 119로 신고해주세요");
        directionMap.put("3-S-far", "엔진을 끄고 대피한 후 119로 신고해주세요");
        directionMap.put("3-M-near", "엔진을 끄고 연기를 피해 유도등을 따라 대비하세요");
        directionMap.put("3-M-mid", "엔진을 끄고 연기를 피해 유도등을 따라 대비하세요");
        directionMap.put("3-M-far", "엔진을 끄고 연기를 피해 유도등을 따라 대비하세요");
        directionMap.put("3-L-near", "엔진을 끄고 연기를 피해 유도등을 따라 대비하세요");
        directionMap.put("3-L-mid", "엔진을 끄고 연기를 피해 유도등을 따라 대비하세요");
        directionMap.put("3-L-far", "엔진을 끄고 연기를 피해 유도등을 따라 대비하세요");

        directionMap.put("3-out", "터널 내로 진입을 멈추고, 우회하세요.");
    }

    public static MulticastMessage handleAccident(AccidentRequestDto.AccidentDto accidentDto,
                                                  LocalDateTime localDateTime,
                                                  List<String> tokens,
                                                  Boolean isTunnel,
                                                  Integer distance) {
        if (tokens == null || tokens.isEmpty()) {
            return null;
        }
        String type = "";
        switch (accidentDto.getType()) {
            case 1:
                type = "홍수";
                break;
            case 2:
                type = "추돌";
                break;
            case 3:
                type = "화재";
                break;
        }

        String size = "";
        switch (accidentDto.getAccidentSize().toString()) {
            case "S":
                size = "소형";
                break;
            case "M":
                size = "중형";
                break;
            case "L":
                size = "대형";
                break;
        }

        String dis = "";
        switch (distance) {
            case 200:
                dis = "near";
                break;
            case 400:
                dis = "mid";
                break;
            case 1000:
                dis ="far";
                break;
            case 0:
                dis="out";
                break;
        }

        // type과 size에 따른 지침을 가져와서 출력
        String directionKey = "";
        if (isTunnel) directionKey = accidentDto.getType() + "-" + accidentDto.getAccidentSize()+"-"+dis;
        else directionKey = accidentDto.getType() + "-" + dis;
        String direction = directionMap.getOrDefault(directionKey, "지침을 찾을 수 없습니다.");

        ZonedDateTime kstDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        Notification notification = Notification.builder()
                .setTitle(direction)
                .setBody(kstDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))+
                        "시 경 " +
                        accidentDto.getTunnel() + " " + accidentDto.getPosition() +
                        "m " + size + " " +
                        type +
                        " 사고 발생")
                .build();

        // 멀티캐스트 메시지를 위한 빌더
        return MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(notification)
                .build();
    }
}
