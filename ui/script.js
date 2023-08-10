// When the page loads, fetch all the boards from the server and display them **************************************
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

//Get all boards **************************************************************************************************
function fetchBoards() {
  return fetch('http://localhost:8080/api/board')
    .then(response => response.json())
    .then(data => {
      if (data.length === 0) {
        return createDefaultBoard().then(() => fetchBoards());
      } else {
        displayBoards(data);
        return data;
      }
    })
    .catch(error => {
      console.error('Error fetching boards:', error);
      return [];
    });
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

async function createDefaultBoard() {
  const newBoard = {
    title: 'Sprint Board 2023'
  };

  try {
    const response = await fetch('http://localhost:8080/api/board', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newBoard)
    });

    const createdBoard = await response.json();
    console.log('Default board created:', createdBoard);
  } catch (error) {
    console.error('Error creating default board:', error);
  }
}


//Update title of board *******************************************************************************************
function updateBoardTitle() {
  const newBoardTitleInput = document.getElementById('newBoardTitle');
  const newTitle = newBoardTitleInput.value;

  if (newTitle.trim() === '') {
    alert("Please enter a valid board title.");
    return;
  }

  fetch(`http://localhost:8080/api/board/${selectedBoardId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ title: newTitle }),
  })
    .then(response => response.json())
    .then(updatedBoard => {
      // Update the board title in the UI
      const boardSelect = document.getElementById('boardSelect');
      const selectedOption = boardSelect.querySelector(`option[value="${selectedBoardId}"]`);
      selectedOption.textContent = newTitle; // Update the text of the selected option

      newBoardTitleInput.value = ''; // Clear the input
    })
    .catch(error => {
      console.error('Error updating board title:', error);
    });
}


//Get all sections ************************************************************************************************
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

//Create section box
function renderSectionsAndCards(sections) {
  const boardContainer = document.querySelector('.board-container');
  boardContainer.innerHTML = '';

  sections.forEach(section => {
    const sectionContainer = document.createElement('div');
    sectionContainer.className = 'section';
    sectionContainer.setAttribute('data-id', section.id); // Add data-id attribute

    const sectionHeader = document.createElement('div');
    sectionHeader.className = 'section-header';

    const sectionTitle = document.createElement('div');
    sectionTitle.className = 'section-title';
    sectionTitle.textContent = section.name;

    const createCardBtn = document.createElement('div');
    createCardBtn.className = 'create-card-btn';
    createCardBtn.textContent = '+ Create Card';
    createCardBtn.addEventListener('click', () => createCard(section.id)); // Pass the section ID

    const cardsContainer = document.createElement('div');
    cardsContainer.className = 'cards-container';

    sectionHeader.appendChild(sectionTitle);
    sectionHeader.appendChild(createCardBtn);
    sectionContainer.appendChild(sectionHeader);
    sectionContainer.appendChild(cardsContainer);

    boardContainer.appendChild(sectionContainer);

    // Fetch and render cards for the current section and board ***************************************************
    fetch(`http://localhost:8080/api/board/${selectedBoardId}/sections/${section.id}/cards`)
      .then(response => response.json())
      .then(cardsData => renderCardsInSection(cardsData, cardsContainer))
      .catch(error => {
        console.error('Error fetching cards:', error);
      });
  });
}

//Create card box
function renderCardsInSection(cardsData, cardsContainer) {
  cardsContainer.innerHTML = '';

  cardsData.forEach(card => {
    const cardContainer = document.createElement('div');
    cardContainer.className = 'card';

    const cardContent = document.createElement('div');
    cardContent.className = 'card-content';

    //For card id ***********************************
    const cardIdElement = document.createElement('div');
    cardIdElement.className = 'card-id';

    const idBold = document.createElement('span');
    idBold.textContent = 'Card ID: ';
    idBold.style.fontWeight = 'bold';

    const idText = document.createElement('span');
    idText.textContent = card.id;

    cardIdElement.appendChild(idBold);
    cardIdElement.appendChild(idText);

    //For card title ***********************************
    const cardTitleElement = document.createElement('div');
    cardTitleElement.className = 'card-title';

    const titleBold = document.createElement('span');
    titleBold.textContent = 'Card Title: ';
    titleBold.style.fontWeight = 'bold';

    const titleText = document.createElement('span');
    titleText.textContent = card.title;

    cardTitleElement.appendChild(titleBold);
    cardTitleElement.appendChild(titleText);

    //For card description ***********************************
    const cardDescriptionElement = document.createElement('div');
    cardDescriptionElement.className = 'card-description';

    const descriptionBold = document.createElement('span');
    descriptionBold.textContent = 'Card Description: ';
    descriptionBold.style.fontWeight = 'bold';

    const descriptionText = document.createElement('span');
    descriptionText.textContent = card.description;

    cardDescriptionElement.appendChild(descriptionBold);
    cardDescriptionElement.appendChild(descriptionText);

    cardContent.appendChild(cardIdElement);
    cardContent.appendChild(cardTitleElement);
    cardContent.appendChild(cardDescriptionElement);

    const actionsContainer = document.createElement('div');
    actionsContainer.className = 'actions';

    const updateIcon = document.createElement('span');
    updateIcon.className = 'update-icon';
    updateIcon.innerHTML = '&#9998;'; // Pen icon
    updateIcon.onclick = () => showUpdateForm(card, cardContainer);

    const deleteIcon = document.createElement('span');
    deleteIcon.className = 'delete-icon';
    deleteIcon.innerHTML = 'X'; // Cross icon
    deleteIcon.onclick = () => deleteCard(card.id, cardContainer);

    actionsContainer.appendChild(updateIcon);
    actionsContainer.appendChild(deleteIcon);

    cardContainer.appendChild(cardContent);
    cardContainer.appendChild(actionsContainer);

    cardsContainer.appendChild(cardContainer);
  });
}


//Create button show box create card ******************************************************************************
function createCard(sectionId) {
  const section = document.querySelector(`.section[data-id="${sectionId}"]`);
  const existingCardForm = section.querySelector('.card-form');
  
  if (!existingCardForm) {
    const cardContainer = document.createElement('div');
    cardContainer.className = 'card';
  
    const cardContent = document.createElement('div');
    cardContent.className = 'card-content';
    const cardAdded = document.createElement('div');
    cardContent.className = 'card-add';
  
    const cardTitleInput = document.createElement('input');
    cardTitleInput.id = 'cardTitleInput';
    cardTitleInput.type = 'text';
    cardTitleInput.placeholder = 'Enter card title';
  
    const cardDescriptionInput = document.createElement('input');
    cardDescriptionInput.id = 'cardDescriptionInput';
    cardDescriptionInput.type = 'text';
    cardDescriptionInput.placeholder = 'Enter a description';
  
    const saveButton = document.createElement('button');
    saveButton.id = 'saveBtn';
    saveButton.textContent = 'Create';
    saveButton.addEventListener('click', () => saveCard(sectionId));
  
    const cancelButton = document.createElement('button');
    cancelButton.id = 'cancleBtn';
    cancelButton.textContent = 'Cancel';
    cancelButton.addEventListener('click', () => {
      cardContainer.remove();
    });
  
    cardAdded.appendChild(cardTitleInput);
    cardAdded.appendChild(cardDescriptionInput);
    cardAdded.appendChild(document.createElement('br')); // New line
    cardAdded.appendChild(saveButton);
    cardAdded.appendChild(cancelButton);
    cardContent.appendChild(cardAdded);
  
    cardContainer.appendChild(cardContent);
    section.appendChild(cardContainer);
  }
  
  
}

//Save card in database
function saveCard(sectionId) {
  const cardTitleInput = document.getElementById('cardTitleInput');
  const cardDescriptionInput = document.getElementById('cardDescriptionInput');

  const newCard = {
    title: cardTitleInput.value,
    description: cardDescriptionInput.value,
    section: { id: sectionId },
    board: { id: selectedBoardId }, // Include the selected board ID
  };

  fetch('http://localhost:8080/api/card', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newCard),
  })
    .then(response => response.json())
    .then(createdCard => {
      if (createdCard) {
        fetchSectionsAndCardsForBoard(selectedBoardId); // Refresh sections/cards
      }
    })
    .catch(error => {
      console.error('Error creating card:', error);
    });
}


//Update card details *********************************************************************************************
function renderCardDetails(card, cardContainer) {
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

  const updateButton = document.createElement('button');
  updateButton.textContent = 'Update';
  updateButton.addEventListener('click', () => showUpdateForm(card, cardContainer));

  cardContent.appendChild(cardIdElement);
  cardContent.appendChild(cardTitleElement);
  cardContent.appendChild(cardDescriptionElement);
  cardContent.appendChild(updateButton);

  cardContainer.appendChild(cardContent);
}

function showUpdateForm(card, cardContainer) {
  const cardContent = cardContainer.querySelector('.card-content');
  cardContent.innerHTML = ''; // Clear existing content

  const cardTitleInput = document.createElement('input');
  cardTitleInput.id = 'updateCardTitleInput';
  cardTitleInput.type = 'text';
  cardTitleInput.value = card.title;

  const cardDescriptionInput = document.createElement('input');
  cardDescriptionInput.id = 'updateCardDescriptionInput';
  cardDescriptionInput.type = 'text';
  cardDescriptionInput.value = card.description;

  const sectionSelect = document.createElement('select');
  sectionSelect.id = 'updateSectionSelect';

  // Fetch and populate the sections for the dropdown
  fetch(`http://localhost:8080/api/board/${selectedBoardId}/section`)
    .then(response => response.json())
    .then(sections => {
      sections.forEach(section => {
        const option = document.createElement('option');
        option.value = section.id;
        option.textContent = section.name;
        sectionSelect.appendChild(option);
      });

      // Pre-select the current section
      sectionSelect.value = card.section.id;
    })
    .catch(error => {
      console.error('Error fetching sections:', error);
    });

  const saveButton = document.createElement('button');
  saveButton.textContent = 'Save';
  saveButton.id = 'saveBtn';
  saveButton.addEventListener('click', () => saveUpdatedCard(card.id));

  const cancelButton = document.createElement('button');
  cancelButton.textContent = 'Cancel';
  cancelButton.id = 'cancleBtn';
  cancelButton.addEventListener('click', () => {
    cardContainer.innerHTML = ''; // Remove the update form
    renderCardDetails(card, cardContainer);
  });

  cardContent.appendChild(cardTitleInput);
  cardContent.appendChild(document.createElement('br')); // New line
  cardContent.appendChild(cardDescriptionInput);
  cardContent.appendChild(document.createElement('br')); // New line
  cardContent.appendChild(sectionSelect);
  cardContent.appendChild(saveButton);
  cardContent.appendChild(cancelButton);
}

function saveUpdatedCard(cardId) {
  const updatedTitleInput = document.getElementById('updateCardTitleInput');
  const updatedDescriptionInput = document.getElementById('updateCardDescriptionInput');
  const updatedSectionSelect = document.getElementById('updateSectionSelect');

  const updatedCard = {
    title: updatedTitleInput.value,
    description: updatedDescriptionInput.value,
    section: { id: updatedSectionSelect.value },
  };

  fetch(`http://localhost:8080/api/card/${cardId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updatedCard),
  })
    .then(response => response.json())
    .then(updatedCardData => {
      fetchSectionsAndCardsForBoard(selectedBoardId); // Refresh sections/cards
    })
    .catch(error => {
      console.error('Error updating card:', error);
    });
}


//Delete card *****************************************************************************************************
function deleteCard(cardId, cardContainer) {
  const shouldDelete = window.confirm("Are you sure you want to delete this card?");
  if (shouldDelete) {
    fetch(`http://localhost:8080/api/card/${cardId}`, {
      method: 'DELETE',
    })
      .then(response => {
        if (response.ok) {
          cardContainer.remove(); // Remove the card container from the UI
        } else {
          console.error('Error deleting card:', response.statusText);
        }
      })
      .catch(error => {
        console.error('Error deleting card:', error);
      });
  }
}





//Create Board *****************************************************************************************************
function openCreateBoardModal() {
  const modal = document.getElementById('createBoardModal');
  modal.style.display = 'block';
}

function closeCreateBoardModal() {
  const modal = document.getElementById('createBoardModal');
  modal.style.display = 'none';
  document.getElementById('newBoardTitleModal').value = ''; // Clear the input field in the modal
}

// Function to create a new board
function createNewBoard() {
  const newBoardTitleInput = document.getElementById('newBoardTitleModal'); // Use the input field from the modal
  const newBoardTitle = newBoardTitleInput.value;

  if (newBoardTitle.trim() === '') {
    alert('Please enter a valid board title.');
    return;
  }
  else{
    alert('Board is created.');
  }

  const newBoard = {
    title: newBoardTitle
  };

  fetch('http://localhost:8080/api/board', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newBoard),
  })
    .then(response => response.json())
    .then(createdBoard => {
      if (createdBoard) {
        // Update the select option with the new board
        const boardSelect = document.getElementById('boardSelect');
        const newBoardOption = document.createElement('option');
        newBoardOption.textContent = createdBoard.title;
        newBoardOption.value = createdBoard.id;
        boardSelect.appendChild(newBoardOption);

        // Clear the input field and close the modal
        newBoardTitleInput.value = '';
        closeCreateBoardModal();
      }
    })
    .catch(error => {
      console.error('Error creating board:', error);
    });
}
