#!/bin/bash

# Base URL for the API
BASE_URL="http://localhost:8080"

# Test Card Controller - GET /cards without parameters

echo "Testing GET /cards without parameters"
curl -X GET "$BASE_URL/cards"
echo -e "\n\n"

# Test Card Controller - GET /cards with query parameters

echo "Testing GET /cards with query parameters"
curl -X GET "$BASE_URL/cards?set=core&page=1&pageSize=50&sort=name&order=asc&rarity=common&mana_cost=3&color=red&type=creature&name=fire"
echo -e "\n\n"

# Test Deck Controller - GET /decks

echo "Testing GET /decks"
curl -X GET "$BASE_URL/decks"
echo -e "\n\n"

# Test Deck Controller - POST /decks

echo "Testing POST /decks"
curl -X POST "$BASE_URL/decks" \
     -H "Content-Type: application/json" \
     -d '{"deckName": "My New Deck"}'
echo -e "\n\n"

# Test Deck Controller - GET /decks/1

echo "Testing GET /decks/1"
curl -X GET "$BASE_URL/decks/1"
echo -e "\n\n"

# Test Deck Controller - PUT /decks/1

echo "Testing PUT /decks/1"
curl -X PUT "$BASE_URL/decks/1" \
     -H "Content-Type: application/json" \
     -d '{"deckName": "Updated Deck Name"}'
echo -e "\n\n"

# Test Deck Controller - DELETE /decks/1

echo "Testing DELETE /decks/1"
curl -X DELETE "$BASE_URL/decks/1"
echo -e "\n\n"

# Test Deck Controller - GET /decks/1/stats

echo "Testing GET /decks/1/stats"
curl -X GET "$BASE_URL/decks/1/stats"
echo -e "\n\n"

# Test Deck Controller - GET /decks/1/random

echo "Testing GET /decks/1/random"
curl -X GET "$BASE_URL/decks/1/random"
echo -e "\n\n"

# Test User Controller - GET /users/me

echo "Testing GET /users/me"
curl -X GET "$BASE_URL/users/me"
echo -e "\n\n"

echo "All tests executed." 