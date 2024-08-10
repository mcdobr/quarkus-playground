package me.mcdobr.customer;

public class CustomerDto {
    private Long id;


    public CustomerDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
