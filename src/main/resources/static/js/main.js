//console.log("main.js loaded.");

// 쿠키에서 key가 일치하는 value를 얻어오기 함수

// 쿠키는 "K=V;, K=V, K=V..." 형식

// 배열.map(함수) : 배열의 각 요소를 이용해 함수를 수행 후 
//                  결과 값으로 새로운 배열을 만들어서 변환

const getCookie = () => {
    const cookies = document.cookie; // "K=V; K=V"

    // cookies 문자열을 배열 형태로 변환
    const cookieList = cookies.split("; ") // ["K=v, "K=V]
                    .map( el => el.split("=") ); // ["K","V"]..

    //console.log(cookieList);

    // 배열 -> 객체로 변환 (그래야 다루기 쉽다)

    const obj = {}; // 비어있는 객체 선언

    for(let i=0; i<cookieList.length; i++) {
        const k = cookieList[i][0]; // key 값
        const v = cookieList[i][1]; // value 값
        obj[k] = v; // 객체에 추가
    }

    console.log(obj);
}

getCookie();