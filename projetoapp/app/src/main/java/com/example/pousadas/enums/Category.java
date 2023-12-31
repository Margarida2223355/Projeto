package com.example.pousadas.enums;

public enum Category {
    ALMOCO("Almoco"),
    JANTAR("Jantar");

    private String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public static Category getFromString(String text) {
        for (Category enumCategory : Category.values()) {
            if (enumCategory.category.equalsIgnoreCase(text)) {
                return enumCategory;
            }
        }

        throw new IllegalArgumentException("Não existe!");
    }
}
