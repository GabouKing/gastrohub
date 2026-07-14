ALTER TABLE menu_items
    DROP FOREIGN KEY fk_menu_items_restaurant;

ALTER TABLE menu_items
    ADD CONSTRAINT fk_menu_items_restaurant
        FOREIGN KEY (restaurant_id)
        REFERENCES restaurants(id)
        ON DELETE CASCADE;

ALTER TABLE restaurants
    DROP FOREIGN KEY user_fk;

ALTER TABLE restaurants
    ADD CONSTRAINT user_fk
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE;
