-- Insert sample users
INSERT INTO users (sub, email) VALUES ('user1', 'user1@example.com');
INSERT INTO users (sub, email) VALUES ('user2', 'user2@example.com');

-- Insert sample cards
INSERT INTO cards (api_id, name, mana_cost, cmc) VALUES ('card001', 'Sample Card 1', '{2}{W}', 3);
INSERT INTO cards (api_id, name, mana_cost, cmc) VALUES ('card002', 'Sample Card 2', '{1}{U}', 2);
INSERT INTO cards (api_id, name, mana_cost, cmc) VALUES ('card003', 'Sample Card 3', '{3}{B}', 4);
INSERT INTO cards (api_id, name, mana_cost, cmc) VALUES ('card004', 'Sample Card 4', '{4}{R}', 5);
INSERT INTO cards (api_id, name, mana_cost, cmc) VALUES ('card005', 'Sample Card 5', '{5}{G}', 6);

-- Insert sample decks for user1
INSERT INTO decks (user_sub, deck_name, deck_format, deck_description, created_at, updated_at) 
  VALUES ('user1', 'First Deck', 'Standard', 'User1 first deck', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO decks (user_sub, deck_name, deck_format, deck_description, created_at, updated_at) 
  VALUES ('user1', 'Second Deck', 'Modern', 'User1 second deck', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample deck for user2
INSERT INTO decks (user_sub, deck_name, deck_format, deck_description, created_at, updated_at) 
  VALUES ('user2', 'User2 Deck', 'Legacy', 'User2 deck example', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert deck-card mappings
-- Assuming deck IDs: First Deck = 1, Second Deck = 2, User2 Deck = 3 and card IDs: 1 to 5
INSERT INTO deck_cards (deck_id, card_id, quantity) VALUES (1, 1, 2);
INSERT INTO deck_cards (deck_id, card_id, quantity) VALUES (1, 2, 3);
INSERT INTO deck_cards (deck_id, card_id, quantity) VALUES (2, 3, 1);
INSERT INTO deck_cards (deck_id, card_id, quantity) VALUES (2, 4, 4);
INSERT INTO deck_cards (deck_id, card_id, quantity) VALUES (3, 5, 2);
INSERT INTO deck_cards (deck_id, card_id, quantity) VALUES (3, 1, 1); 