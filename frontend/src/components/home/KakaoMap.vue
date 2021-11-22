<template>
  <div>
    <div id="map" v-show="!loading"></div>
    <b-card v-show="loading">
      <b-skeleton-img></b-skeleton-img>
    </b-card>
  </div>
</template>

<script>
import { mapState } from "vuex";
const houseStore = "houseStore";

export default {
  name: "KakaoMap",
  computed: {
    ...mapState(houseStore, ["house", "houses"]),
  },
  watch: {
    houses() {
      this.initMap();
    },
    house() {
      this.displaySelectMarker(this.house);
    },
  },
  data() {
    return {
      map: null,
      pa: null,
      markers: [],
      customs: [],
      infowindos: null,
      customOverlay: null,
      geocoder: null,
      loading: false,
      place: {},
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /*global kakao*/
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=bb5f4f03bff67d690a067716317ad683&libraries=services";
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3, // 지도의 확대 레벨
      };
      this.map = new kakao.maps.Map(container, options);

      this.ps = new kakao.maps.services.Places(); // 장소 검색 객체
      this.geocoder = new kakao.maps.services.Geocoder();
      this.markers = [];
      this.customs = [];
      this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

      // console.log(this.houses.length);
      if (this.houses.length > 0) {
        try {
          this.houses.map((house) => {
            // console.log(house);
            (async () => {
              var address = house.houseinfo.address;
              // console.log(address);

              let [lat, lng] = await this.addressSearch(address);
              if (lat == null) {
                house.lat = "";
                house.lng = "";
              } else {
                house.lat = lat;
                house.lng = lng;
              }
            })();
          });
        } finally {
          this.loading = true;
          setTimeout(() => {
            this.displayMarker(this.houses);
            this.loading = false;
          }, 1100);
        }
      } else {
        // this.loading = true;
      }
    },

    addressSearch(address) {
      return new Promise((resolve) => {
        this.geocoder.addressSearch(address, function (result, status) {
          if (status === kakao.maps.services.Status.OK) {
            let lat = result[0].y;
            let lng = result[0].x;
            resolve([lat, lng]);
          } else {
            resolve([null, null]);
          }
        });
      });
    },

    displaySelectMarker(place) {
      if (place.lat && place.lat != "" && typeof place.lat != "undefined") {
        this.removeMarker();
        this.closeOverlay();
        var fragment = document.createDocumentFragment();
        var bounds = new kakao.maps.LatLngBounds();

        var placePosition = new kakao.maps.LatLng(place.lat, place.lng);
        var marker = this.addMarker(placePosition, -1);
        var itemEl = this.getListItem(0, place);

        itemEl.onmouseover = () => {
          return this.displayInfowindow(marker, place);
        };

        itemEl.onmouseout = () => {
          return this.customOverlay.setMap(null);
        };

        fragment.appendChild(itemEl);
        bounds.extend(placePosition);
        this.map.setBounds(bounds);

        // ----------- 커스텀 오버레이 생성---------------
        const random = Math.floor(Math.random() * 13) + 1;
        const img = "apt" + random + ".jpg";

        var sido =
          place.houseinfo.sidoName == null ? "" : place.houseinfo.sidoName;
        var gugun =
          place.houseinfo.gugunName == null ? "" : place.houseinfo.gugunName;
        var dong =
          place.houseinfo.dongName == null ? "" : place.houseinfo.dongName;
        var jibun = place.houseinfo.jibun == null ? "" : place.houseinfo.jibun;
        if (place.lat == null) return;

        var content =
          `
		<div class="overlaybox" style="border: 1px solid black; background-color: white; padding: 3px 10px; border-radius:5px;">
			<div class="boxtitle pt-2">
				<h5>${place.houseinfo.aptName}<h5>
			</div>
			<div class="first">
      <b-img :src="require('@/assets/apt/${img}')" style="width:247px; height:136px;"</b-img>
      </div>
			<ul style="list-style: none; padding: 0px;">
				<li class="up" style="list-style: none;">
					<span class="title" style="font-weight: bolder">건축년도</span>
					<span class="count">${place.houseinfo.buildYear}</span>
				</li>
				<li style="list-style: none;">
					<span class="title" style="font-weight: bolder">주소</span>
					<span class="count">` +
          sido +
          ` ` +
          gugun +
          ` ` +
          dong +
          ` ` +
          jibun +
          `</span>
				</li>
				<li style="list-style: none;">
					<span class="title" style="font-weight: bolder">거래금액</span>
					<span class="count">${place.houseinfo.recentPrice}</span>
				</li>
        <li style="list-style: none;">
					<span class="title" style="font-weight: bolder">층수</span>
					<span class="count">${place.floor} 층</span>
				</li>
			</ul>
		</div>
	`;
        // 커스텀 오버레이가 표시될 위치
        var position = new kakao.maps.LatLng(
          place.lat - 0.00073,
          place.lng - 0.00003
        );

        // 커스텀 오버레이를 생성
        var customOverlay = new kakao.maps.CustomOverlay({
          position: position,
          content: content,
          xAnchor: 0.3,
          yAnchor: 0.91,
        });

        // 커스텀 오버레이를 지도에 표시
        customOverlay.setMap(this.map);
        this.customs.push(customOverlay);
      }
    },

    displayMarker(places) {
      var fragment = document.createDocumentFragment();
      var bounds = new kakao.maps.LatLngBounds();

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker(); // 기존에 찍혀있던 마커 지움
      for (var i = 0; i < places.length; i++) {
        if (i == 15) break;
        if (
          typeof places[i].lat != "undefined" &&
          places[i].lat != null &&
          places[i].lat != ""
        ) {
          var placePosition = new kakao.maps.LatLng(
            places[i].lat,
            places[i].lng
          );

          var marker = this.addMarker(placePosition, i);
          var itemEl = this.getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

          // 검색된 장소 위치를 기준으로 지도 범위를 재설정
          bounds.extend(placePosition);
          this.place = places[i];
          // console.log(this.place);

          // 마커를 클릭하면 세부정보가 뜸
          // kakao.maps.event.addListener(marker, "click", () => {
          //   // var place = places[i];
          //   return this.displayInfowindow(marker, this.place);
          // });

          itemEl.onmouseover = () => {
            return this.displayInfowindow(marker, this.place);
          };

          itemEl.onmouseout = () => {
            return this.customOverlay.setMap(null);
          };

          fragment.appendChild(itemEl);
        }
      }
      // 마커를 생성하고 지도에 표시합니다

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      this.map.setBounds(bounds);
    },

    displayInfowindow(marker, place) {
      var position = new kakao.maps.LatLng(
        marker.getPosition().getLat() + 0.00033,
        marker.getPosition().getLng() - 0.00003
      );
      // var lat = marker.getPosition().getLat() + 0.00033;
      // var lng = marker.getPosition().getLng() - 0.00003;
      const random = Math.floor(Math.random() * 13) + 1;
      const img = "apt" + random + ".jpg";

      var sido =
        place.houseinfo.sidoName == null ? "" : place.houseinfo.sidoName;
      var gugun =
        place.houseinfo.gugunName == null ? "" : place.houseinfo.gugunName;
      var dong =
        place.houseinfo.dongName == null ? "" : place.houseinfo.dongName;
      var jibun = place.houseinfo.jibun == null ? "" : place.houseinfo.jibun;
      if (place.lat == null) return;

      var content =
        `
		<div class="overlaybox" style="background-color: white; padding: 3px; border-radius:5px;">
			<div class="boxtitle">
				<span>${place.houseinfo.aptName}<span>
			</div>
			<div class="first">
      <b-img :src="require('@/assets/apt/${img}')" style="width:247px; height:136px;"</b-img>
      </div>
			<ul style="list-style: none; padding: 0px;">
				<li class="up" style="list-style: none;">
					<span class="title" style="font-weight: bolder">건축년도</span>
					<span class="count">${place.houseinfo.buildYear}</span>
				</li>
				<li style="list-style: none;">
					<span class="title" style="font-weight: bolder">주소</span>
					<span class="count">` +
        sido +
        ` ` +
        gugun +
        ` ` +
        dong +
        ` ` +
        jibun +
        `</span>
				</li>
				<li style="list-style: none;">
					<span class="title" style="font-weight: bolder">거래금액</span>
					<span class="count">${place.houseinfo.recentPrice} 만원</span>
				</li>
			</ul>
		</div>
	`;
      new kakao.maps.CustomOverlay({
        position: position,
        content: content,
        xAnchor: 0.3,
        yAnchor: 0.91,
      }).setMap(this.map);

      this.customs = [
        ...this.customs,
        new kakao.maps.CustomOverlay({
          position: position,
          content: content,
          xAnchor: 0.3,
          yAnchor: 0.91,
        }),
      ];
    },

    //마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, idx) {
      if (idx == -1) {
        // 선택된 마커일 때
        let imageSrc =
            "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png",
          imageSize = new kakao.maps.Size(24, 35),
          markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
          marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage, // 마커 이미지
          });

        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

        return marker;
      } else {
        let imageSrc =
            "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
          imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
          imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
          },
          markerImage = new kakao.maps.MarkerImage(
            imageSrc,
            imageSize,
            imgOptions
          ),
          marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage,
            clickable: true,
          });

        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

        return marker;
      }
    },

    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },

    closeOverlay() {
      for (var i = 0; i < this.customs.length; i++) {
        this.customs[i].setMap(null);
      }
      this.customs = [];
    },

    //검색결과 항목을 Element로 반환하는 함수입니다
    getListItem(index, place) {
      var sido =
        place.houseinfo.sidoName == null ? "" : place.houseinfo.sidoName;
      var gugun =
        place.houseinfo.gugunName == null ? "" : place.houseinfo.gugunName;
      var dong =
        place.houseinfo.dongName == null ? "" : place.houseinfo.dongName;
      var jibun = place.houseinfo.jibun == null ? "" : place.houseinfo.jibun;
      var el = document.createElement("li");
      var itemStr =
        `
          <span class="markerbg marker_${index + 1}></span>
          <div class="info"><h5>${
            place.houseinfo.aptName
          }</h5> <button>관심등록</button>
          <span>` +
        sido +
        ` ` +
        gugun +
        ` ` +
        dong +
        ` ` +
        jibun +
        `</span>
        `;
      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },

    //검색결과 목록의 자식 Element를 제거하는 함수입니다
    removeAllChildNods(el) {
      while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
      }
    },
  },
  // //검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
  // //인포윈도우에 장소명을 표시합니다
  // displayInfowindow(marker, title, place) {
  //   var position = new kakao.maps.LatLng(
  //     marker.getPosition().getLat() + 0.00033,
  //     marker.getPosition().getLng() - 0.00003
  //   );
  //   var lat = marker.getPosition().getLat() + 0.00033;
  //   var lng = marker.getPosition().getLng() - 0.00003;
  //   // const random = Math.floor(Math.random() * 13) + 1;
  //   // const img = "apt" + random + ".jpg";

  //   var sido = place.sidoName == null ? "" : place.sidoName;
  //   var gugun = place.gugunName == null ? "" : place.gugunName;
  //   var dong = place.dongName == null ? "" : place.dongName;
  //   var jibun = place.jibun == null ? "" : place.jibun;
  //   if (place.lat == null) return;

  //   // <div class="first"><img src="${root}/img/apt/${img}" style="width:247px; height:136px;" alt=""></div>
  //   var content =
  //     `
  //     <div class="overlaybox">
  //       <div class="boxtitle">
  //         <span>${title}<span>
  //         <button class="btn btn-warning" onclick='javascript:closeOverlay(` +
  //     lat +
  //     `, ` +
  //     lng +
  //     `)'>X</button>
  //       </div>

  //       <ul>
  //         <li class="up">
  //           <span class="title">건축년도</span>
  //           <span class="count">${place.buildYear}</span>
  //         </li>
  //         <li>
  //           <span class="title">주소</span>
  //           <span class="count">` +
  //     sido +
  //     ` ` +
  //     gugun +
  //     ` ` +
  //     dong +
  //     ` ` +
  //     jibun +
  //     `</span>
  //         </li>
  //         <li>
  //           <span class="title">최신거래금액</span>
  //           <span class="count">${place.recentPrice}</span>
  //         </li>
  //       </ul>
  //     </div>
  //   `;
  //   this.customOverlay = new kakao.maps.CustomOverlay({
  //     position: position,
  //     content: content,
  //     xAnchor: 0.3,
  //     yAnchor: 0.91,
  //   });
  //   this.customOverlay.setMap(this.map);
  //   this.customs.push(this.customOverlay);
  // },
};
</script>

<style scoped>
#map {
  min-width: 400px;
  height: 400px;
}
</style>
