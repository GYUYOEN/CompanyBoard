const canvas = document.getElementById("canvas");
const ctx = canvas.getContext("2d");

// 룰렛에 들어갈 항목
const product = [];

// 각 항목에 대한 색상
const colors = [];

// 메뉴 추가
function addMenu() {
    const menu = document.getElementById("inputMenu").value;

    if(menu == "") {
        alert("메뉴를 입력해주세요.")
        return
    }

    let data = {
        "menu" : menu
    }

    $.ajax({
        url : "roulette/add",
        type : "post",
        data: data,
        success : function (menu) {
            if(menu == "fail") {
                alert("중복된 메뉴가 존재합니다.");
                return
            }

            $("#checkboxMenu").append("<div class='form-check'>" +
                                        "<input class='form-check-input' name='menu' id='selectMenu' type='checkbox' value='" + menu + "'/>" +
                                        "<label class='form-check-label fs-3' for='selectMenu' id='labelMenu'>" + menu + "</label>" +
                                        "<div class='deleteMenu btn-group-sm'>" +
                                            "<button class='btn btn-danger' id='buttonMenu' type='button' onclick='deleteMenu()'>제거</button>" +
                                        "</div>" +
                                      "</div>");


        }
    })
}

// 동적으로 생성한 html의 값
$(document).on('click', 'input[name=menu]', function () {
        const checkboxValue = $(this).val();
        if($(this).is(":checked")) {
            addMenuInRoulette(checkboxValue, true);
        } else {
            addMenuInRoulette(checkboxValue, false);
            return;
        }
    }
)

// 메뉴 삭제
function deleteMenu(element) {
     const menu = element.parentElement.parentElement.children[1].firstChild.data;

     console.log(menu)

     let data = {
         "menu" : menu
     }

     $.ajax({
         url : "roulette/delete",
         type: "delete",
         data: data,
         success : function (menu) {
             alert(menu + "메뉴를 제거하였습니다.");
             location.reload()
         }

     })
 }

$(document).on('click', '#buttonMenu', function () {
    let menu = document.getElementById("inputMenu").value;

    if(menu == "") {
        deleteMenu($(this))
        return
    }

    console.log(menu)

    let data = {
        "menu" : menu
    }

    $.ajax({
        url : "roulette/delete",
        type: "delete",
        data: data,
        success : function (menu) {
            alert(menu + "메뉴를 제거하였습니다.");
            location.reload()
        }

    })
})


function addMenuInRoulette(checkboxValue, isCheck) {
    console.log(isCheck)

    if(isCheck) {
        product.push(checkboxValue);
        colors.push("#" + Math.round(Math.random() * 0xffffff).toString(16));
    } else {
        for(let i = 0; i < product.length; i++) {
            if(product[i] === checkboxValue) {
                product.splice(i)
                colors.splice(i);
            }
        }
    }

    // 그려줄 캔버스의 중점 위치 구하기
    const newMake = () => {

         const [cw, ch] = [canvas.width / 2, canvas.height / 2]; // canvas의 넓이, 높이 절반값
         const arc = Math.PI / (product.length / 2);

         // 룰렛에 그릴 항목의 수에 따른 항목의 크기 값 구하기. 해당 값을 통해 동그란 룰렛에 각 영역을 그리는 방법은 arc 메서드를 이용
         for (let i = 0; i < product.length; i++) {
             ctx.beginPath();
             // 각 칸마다 색칠할 색상 변경
             ctx.fillStyle = colors[i % (colors.length)];
             // 그려줄 펜의 위치를 종점 위치로 옮겨주는 작업
             ctx.moveTo(cw, ch);
             // ctx.arc(x, y, radius, startAngle, endAngle)
             // x,y -> 종점, radius -> 반지름 (x와 같은 값)
             // startAngle, endAngle : 항목 개수로 나눈 크기를 이용해서 (i-1) ~ i 지점까지 만의 원을 그리는 코드 작성
             ctx.arc(cw, ch, cw, arc*i, arc*(i+1));
             ctx.fill();
             ctx.closePath();
         }

         ctx.fillStyle = "#fff";
         ctx.font = "18px Pretendard";
         ctx.textAlign = "center";

         // 그려진 배경 위에 텍스트 그리기
         // save, restore 메서드를 이용해서 속성 값이 적용된 캔버스의 설정 값(이미지 x)을 저장하고 가져올 수 있음.
         // translate, rotate로 인해 변형된 콘텍스트 설정 값을 초기값으로 되돌려 주는 작업을 통해서 텍스트 그려주기
         for (let i = 0; i< product.length; i++) {
             const  angle = (arc*i) + (arc/2);
             ctx.save();
             ctx.translate(
                 cw + Math.cos(angle)*(cw-50),
                 ch + Math.sin(angle)*(ch-50)
             );

             ctx.rotate(angle+Math.PI/2);

             // 항목명에 띄어쓰기가 있을 시 줄바꿈
             product[i].split(" ").forEach((text, j) => {
                 ctx.fillText(text, 0, 30 * j);
             });
             ctx.restore();
         }
     }

    newMake();
}

// 룰렛 돌리기
const rotate = () => {
    // 룰렛 당첨 결정
    // 랜덤 한 난수를 얻을 수 있음. 해당 수에 항목의 개수를 곱해주면 랜덤한 당첨 결과를 얻을 수 있음.
    const ran = Math.floor(Math.random() * product.length);

    // 랜덤한 결과 값에 영역의 크기를 곱해서 룰렛의 위치를 맞추어 주고, 360도룰 10번 돌라는 의미의 3600을 추가로 더해줌.
    // 추가로 오차범위를 조정하기 위해서 (arc * 3)의 수식을 추가로 사용
    const arc = 360 / product.length;
    const rotate = (arc * ran) + 7200 + (arc * (product.length/3));

    canvas.style.transform = `rotate(-${rotate}deg)`;

    setTimeout(() => {
        if(!alert(product[ran])) {
            location.reload()
        }
    },10000);
};

