package page.se3.datingapp.domain.model.account;

public record Account(
        String email,
        String passwordHash,
        Boolean isBanned
) {

    public static Account getInitial(String email, String passwordHash, Boolean isBanned) {
        return new Account(email, passwordHash, isBanned);
    }

    public static Account getSelfInitial(String email, String passwordHash) {
        return getInitial(email, passwordHash, false);
    }
}
