package app.model.enums;

public enum AdStatus {
    UNPUBLISHED("Unpublished"),
    PUBLISHED("Published"),
    PENDING("Pending"),
    REMOVED("Removed");

    private String label;

    AdStatus(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
