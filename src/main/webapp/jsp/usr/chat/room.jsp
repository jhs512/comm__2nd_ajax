<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../common/head.jspf"%>

<script>
function ChatMessageSave__submitForm(form) {
    form.body.value = form.body.value.trim();

    if ( form.body.value.length == 0 ) {
        form.body.focus();

        return false;
    }

    // fetch 방식이 아닌, jquery 방식
    $.post(
        '/usr/chat/writeMessageAjax/${room.id}', // 주소, action
        {
            body: form.body.value // 폼 내용, input name, value
        },
        function(data) { // 콜백 메서드, 통신이 완료된 후, 실행
            // data.resultCode
            // data.msg
        },
        'json' // 받은 데이터를 json 으로 해석하겠다.
    );

    form.body.value = '';
    form.body.focus();
}
</script>


<script>
let ChatMessages__lastId = 0;

function ChatMessages__remove(id, btn) {
    $.ajax({
        url: `/usr/chat/deleteMessageAjax/\${id}`,
        type: 'DELETE',
        success: function(data) {
            if ( data.msg ) {
                alert(data.msg);
            }

            // $(btn).parent().parent().remove();
            // closest : 조상중에서 나와 가장 가까운 사람 1명 찾기
            // $(btn).closest('li') : 나(a)를 기준으로, 나의 조상중에서 나와 가장 가까운 li 하나 찾기
            $(btn).closest('li').remove();
        },
        dataType: 'json'
    });
}

function ChatMessages__modify(form) {
    form.body.value = form.body.value.trim();

    if ( form.body.value.length == 0 ) {
        alert('내용을 입력해주세요.');
        form.body.focus();

        return;
    }

    $.post(
        `/usr/chat/modifyMessageAjax/\${form.id.value}`, // 주소, action
        {
            body: form.body.value
        },
        function(data) {
            if ( data.msg ) {
                alert(data.msg);
            }
        },
        'json' // 받은 데이터를 json 으로 해석하겠다.
    );
}

function ChatMessages__loadMore() {
    fetch(`/usr/chat/getMessages/${room.id}/?fromId=\${ChatMessages__lastId}`)
        .then(data => data.json())
        .then(responseData => {
            const messages = responseData.data;
            for ( const index in messages ) {
                const message = messages[index];

                const html = `
                    <li>
                        <div class="flex">
                            <span>메세지 \${message.id} :</span>
                            &nbsp;
                            <span>\${message.body}</span>
                            &nbsp;
                            <a onclick="if ( confirm('정말로 삭제하시겠습니까?') ) ChatMessages__remove(\${message.id}, this); return false;" class="cursor-pointer hover:underline hover:text-[red] mr-2">삭제</a>
                        </div>
                        <form onsubmit="ChatMessages__modify(this); return false;">
                            <input type="hidden" name="id" value="\${message.id}" />
                            <input type="text" name="body" class="input input-bordered" placeholder="내용" value="\${message.body}" />
                            <button type="submit" class="btn btn-secondary btn-outline">수정</button>
                        </form>
                    </li>
                `;

                $('.chat-messages').append(html);
            }

            if ( messages.length > 0 ) {
                ChatMessages__lastId = messages[messages.length - 1].id;
            }

            // ChatMessages__loadMore(); // 즉시 실행
            setTimeout(ChatMessages__loadMore, 3000); // ChatMessages__loadMore(); 를 3초 뒤에 수행
        });
}
</script>

<section>
    <div class="container px-3 mx-auto">
        <h1 class="font-bold text-lg">채팅방</h1>

        <div>
            ${room.title}
        </div>

        <div>
            ${room.body}
        </div>

        <form onsubmit="ChatMessageSave__submitForm(this); return false;" method="POST" action="/usr/chat/modifyMessage/${room.id}">
            <input autofocus name="body" type="text" placeholder="메세지를 입력해주세요." class="input input-bordered" />
            <button type="submit" value="" class="btn btn-outline btn-primary">
                작성
            </button>
        </form>

        <ul class="chat-messages mt-5">

        </ul>
    </div>
</section>

<script>
ChatMessages__loadMore();
</script>

<%@ include file="../common/foot.jspf"%>