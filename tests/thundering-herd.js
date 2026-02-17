import  http  from 'k6/http';
import { check } from "k6";

let url = 'http://localhost:8080/';

export const options = {
    // thundering herd configuration for VU's. 1000 active at the same time and buys one at a time
    scenarios: {
        contacts: {
            executor: 'per-vu-iterations',
            vus: 1000,
            iterations: 1,
            maxDuration: '30s',
        },
    },
};

export default function () {
    let count = Math.floor(Math.random() * 10000) + 1

    let genUserId = "user_" + count;

    const getTime = new Date().toLocaleTimeString();

    const quantity = 1;
    let data = {
        userId: genUserId,
        clientQuantity: quantity,
        clientTime: getTime
    }

    let res = http.post(url, JSON.stringify(data),
          {
             headers: {
                 'Content-Type': 'application/json'
             }
          }
        )


    check(res, {
            'status is 200 (BOUGHT)': (r) => r.status === 200,
            'status is 409 (SOLD OUT)': (r) => r.status === 409,
            'status is 500 (CRASHED)': (r) => r.status === 500,
    });




}
