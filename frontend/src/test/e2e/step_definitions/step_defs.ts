import { Given, When, Then, DataTable, Before } from '@badeball/cypress-cucumber-preprocessor';

Before(function(){
    cy.visit('/');
    cy.intercept('/store/login').as('login');
});

When(`I login as user {string} and password {string}`, (user: string, password: string) => {
    cy.get('#user').type('1');
    cy.get('#password').type('1');
    cy.get('.v-btn').click();
});

Then(`I log in`, () => {
    cy.wait('@login').its('response.statusCode').should('eq',200);
});