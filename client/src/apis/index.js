import axios from 'axios';

export const apiGo = axios.create({ baseURL: 'http://localhost:8081/api' });
export const apiJava = axios.create({ baseURL: 'http://localhost:8080/api' });
