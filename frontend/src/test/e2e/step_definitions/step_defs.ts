import { Given, When, Then, DataTable, Before } from '@badeball/cypress-cucumber-preprocessor';

Before(function(){
    cy.visit('/');
    cy.intercept('/store/login').as('login');
    cy.intercept('/store/item/orderPrice').as('order');
});

When(`I login as user {string} and password {string}`, (user: string, password: string) => {
    cy.get('#user').type(user);
    cy.get('#password').type(password);
    cy.get('#login').click();
});

Then(`I log in`, () => {
    cy.wait('@login').its('response.statusCode').should('eq',200);
});

When(`I add an item with id {string}`, (arg0: string) => {
    cy.get('#item-'+ arg0).click();
});

Then(`I click the button {string}`, (arg0: string) => {
    cy.get('#btn-'+arg0 ).click();
});

Then(`a notification appears`, () => {
    cy.wait('@order').its('response.statusCode').should('eq',200);
});