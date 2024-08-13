package swiftescaper.backend.swiftescaper.web.dto.accidentDto;

public enum AccidentType {
    FLOOD(1),
    BUMP(2),
    FIRE(3);

    private final int value;

    // 열거형 상수에 정수 값을 할당하는 생성자
    AccidentType(int value) {
        this.value = value;
    }

    // 열거형 상수에 할당된 정수 값을 반환하는 메서드
    public int getValue() {
        return value;
    }

    // 정수 값을 기반으로 열거형 상수를 반환하는 정적 메서드
    public static AccidentType fromInt(int value) {
        for (AccidentType type : AccidentType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
