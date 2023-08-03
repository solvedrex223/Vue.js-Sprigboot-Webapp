/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ItemInfo } from '../models/ItemInfo';
import type { LoginCredentials } from '../models/LoginCredentials';
import type { Order } from '../models/Order';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class DefaultService {

    /**
     * Check the status of the backend.
     * @returns string Literally returns 'Hello world'. Innovative, I know.
     * @throws ApiError
     */
    public static sayHelloToWorld(): CancelablePromise<string> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/store/health',
        });
    }

    /**
     * Get the information of an item.
     * @param id
     * @returns ItemInfo Returns the item info.
     * @throws ApiError
     */
    public static getItemById(
        id: number,
    ): CancelablePromise<ItemInfo> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/store/item/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * Get the total price of the order.
     * @param requestBody configuration data to be appended at the current level
     * @returns number Returns the order total price.
     * @throws ApiError
     */
    public static calcOrder(
        requestBody: Order,
    ): CancelablePromise<number> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/store/item/orderPrice',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * Authorize a login.
     * @param requestBody configuration data to be appended at the current level
     * @returns any Authorized login.
     * @throws ApiError
     */
    public static authLogin(
        requestBody: LoginCredentials,
    ): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/store/login',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                401: `Error with credentials.`,
            },
        });
    }

}
