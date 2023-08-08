// Declare a variable to store the currently selected board ID
let selectedBoardId = null;

// When the page loads, fetch all the boards from the server and display them
window.onload = function() {
  const selectOption = document.createElement('option');
  selectOption.textContent = 'Select board';
  selectOption.disabled = true;
  selectOption.selected = true;
  const boardsList = document.getElementById('boardSelect');
  boardsList.appendChild(selectOption);

  const boardSelect = document.getElementById('boardSelect');
  boardSelect.addEventListener('change', () => {
    selectedBoardId = boardSelect.value; // Save the selected board ID
    if (selectedBoardId) {
      fetchSectionsAndCardsForBoard(selectedBoardId);
    } else {
      const boardContainer = document.querySelector('.board-container');
      boardContainer.innerHTML = '';
    }
  });

  fetchBoards();
};

function fetchBoards() {
  fetch('http://localhost:8080/api/board')
    .then(response => response.json())
    .then(data => displayBoards(data))
    .catch(error => console.error('Error fetching boards:', error));
}

function displayBoards(boards) {
  const boardsList = document.getElementById('boardSelect');
  boardsList.innerHTML = '';
  const selectOption = document.createElement('option');
  selectOption.textContent = 'Select board';
  selectOption.disabled = true;
  selectOption.selected = true;
  boardsList.appendChild(selectOption);

  boards.forEach(board => {
    const boardItem = document.createElement('option');
    boardItem.textContent = board.title;
    boardItem.value = board.id;
    boardsList.appendChild(boardItem);
  });
}

function fetchSectionsAndCardsForBoard(boardId) {
  fetch(`http://localhost:8080/api/board/${boardId}/section`)
    .then(response => response.json())
    .then(data => {
      renderSectionsAndCards(data);
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

function renderSectionsAndCards(sections) {
  const boardContainer = document.querySelector('.board-container');
  boardContainer.innerHTML = '';

  sections.forEach(section => {
    const sectionContainer = document.createElement('div');
    sectionContainer.className = 'section';

    const sectionHeader = document.createElement('div');
    sectionHeader.className = 'section-header';

    const sectionTitle = document.createElement('div');
    sectionTitle.className = 'section-title';
    sectionTitle.textContent = section.name;

    const createCardBtn = document.createElement('div');
    createCardBtn.className = 'create-card-btn';
    createCardBtn.textContent = '+ Create Card';
    createCardBtn.onclick = createCard;

    const cardsContainer = document.createElement('div');
    cardsContainer.className = 'cards-container';

    sectionHeader.appendChild(sectionTitle);
    sectionHeader.appendChild(createCardBtn);
    sectionContainer.appendChild(sectionHeader);
    sectionContainer.appendChild(cardsContainer);

    boardContainer.appendChild(sectionContainer);

    // Fetch and render cards for the current section and board
    fetch(`http://localhost:8080/api/board/${selectedBoardId}/sections/${section.id}/cards`)
      .then(response => response.json())
      .then(cardsData => renderCardsInSection(cardsData, cardsContainer))
      .catch(error => {
        console.error('Error fetching cards:', error);
      });
  });
}

function renderCardsInSection(cardsData, cardsContainer) {
  cardsContainer.innerHTML = '';

  cardsData.forEach(card => {
    const cardContainer = document.createElement('div');
    cardContainer.className = 'card';

    const cardContent = document.createElement('div');
    cardContent.className = 'card-content';

    const cardIdElement = document.createElement('div');
    cardIdElement.className = 'card-id';
    cardIdElement.textContent = `Card ID: ${card.id}`;

    const cardTitleElement = document.createElement('div');
    cardTitleElement.className = 'card-title';
    cardTitleElement.textContent = `Card Title: ${card.title}`;

    const cardDescriptionElement = document.createElement('div');
    cardDescriptionElement.className = 'card-description';
    cardDescriptionElement.textContent = `Card Description: ${card.description}`;

    cardContent.appendChild(cardIdElement);
    cardContent.appendChild(cardTitleElement);
    cardContent.appendChild(cardDescriptionElement);

    const actionsContainer = document.createElement('div');
    actionsContainer.className = 'actions';

    const updateIcon = document.createElement('span');
    updateIcon.className = 'update-icon';
    updateIcon.innerHTML = '&#9998;'; // Pen icon

    const deleteIcon = document.createElement('span');
    deleteIcon.className = 'delete-icon';
    deleteIcon.innerHTML = '&#10060;'; // Cross icon

    actionsContainer.appendChild(updateIcon);
    actionsContainer.appendChild(deleteIcon);

    cardContainer.appendChild(cardContent);
    cardContainer.appendChild(actionsContainer);

    cardsContainer.appendChild(cardContainer);
  });
}


function createCard() {
  // Implementation for creating a card goes here
}






















function createCard(sectionIndex) {
const sections = document.querySelectorAll(".section");
if (sectionIndex < 0 || sectionIndex >= sections.length) return;

const cardsContainer = sections[sectionIndex].querySelector(".cards-container");
if (!cardsContainer) return;

const card = document.createElement("div");
card.classList.add("card");

const cardContent = document.createElement("div");
cardContent.classList.add("card-content");

const cardId = document.createElement("div");
cardId.classList.add("card-id");
cardId.textContent = "Card ID: " + (cardsContainer.children.length + 1);

const cardTitle = document.createElement("div");
cardTitle.classList.add("card-title");
cardTitle.textContent = "New Task";

const cardDescription = document.createElement("div");
cardDescription.classList.add("card-description");
cardDescription.textContent = "Description for the new task";

cardContent.appendChild(cardId);
cardContent.appendChild(cardTitle);
cardContent.appendChild(cardDescription);

const actions = document.createElement("div");
actions.classList.add("actions");

const updateIcon = document.createElement("span");
updateIcon.classList.add("update-icon");
updateIcon.textContent = "✎";
updateIcon.addEventListener("click", () => updateCard(cardId.textContent.split(" ")[2]));

const deleteIcon = document.createElement("span");
deleteIcon.classList.add("delete-icon");
deleteIcon.textContent = "✕";
deleteIcon.addEventListener("click", () => deleteCard(cardId.textContent.split(" ")[2]));

actions.appendChild(updateIcon);
actions.appendChild(deleteIcon);

card.appendChild(cardContent);
card.appendChild(actions);

cardsContainer.appendChild(card);
deleteIcon.addEventListener('click', () => {
card.remove();
});
}

function updateCard(cardId) {
const newTitle = prompt("Enter the updated title for the card:", "Updated Task");
if (newTitle !== null && newTitle !== "") {
const cardTitle = document.querySelector(`.card-id:contains("${cardId}")`).nextElementSibling;
cardTitle.textContent = newTitle;
}
}

function deleteCard(cardId) {
const confirmDelete = confirm("Are you sure you want to delete this card?");
if (confirmDelete) {
const card = document.querySelector(`.card-id:contains("${cardId}")`).parentElement.parentElement;
card.remove();
}
}
